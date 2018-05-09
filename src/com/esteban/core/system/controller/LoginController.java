package com.esteban.core.system.controller;

import com.esteban.core.framework.annotation.NeedLog;
import com.esteban.core.framework.utils.CookieUtils;
import com.esteban.core.framework.utils.MD5;
import com.esteban.core.framework.utils.WebUtils;
import com.esteban.core.system.model.Oper;
import com.esteban.core.system.model.OperExample;
import com.esteban.core.system.service.IOperLogic;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author esteban
 * @since 2014年5月23日
 */
@Controller
@RequestMapping("")
public class LoginController {

    private static final Logger log = Logger
            .getLogger(LoginController.class);

    protected static MD5 md5 = new MD5();

    private HashMap<String, String> INFOMAP = new HashMap<String, String>() {
        private static final long serialVersionUID = 1L;
        {
            put("nameNotNull", "用户名不能为空");
            put("passNotNull", "密码不能为空");
            put("codeNotNull", "验证码不能为空");
            put("codeErro", "验证码错误");
            put("isBlack", "抱歉,该用户被列为黑名单,不能进行登陆");
            put("infoErro", "用户名密码错误");
            put("userNotPass", "该用户信息暂未进行审核，请耐心等待");
            put("userNotActive", "该用户审核不通过或未激活,请重新申请或激活");
            put("userNotExist", "用户不存在");
        }
    };

    @Resource
    private IOperLogic operLogic;

    @ResponseBody
    @NeedLog(operateContent = "用户登录")
    @RequestMapping("/login")
    public Map<String,Object> login(HttpServletRequest req, HttpServletResponse res, String userId,
                     String passwd, String checkCode) {
        Map<String,Object> result=new HashMap<>();
        String flag="";
        //数据验证
        if (StringUtils.isBlank(userId)) {
            flag="nameNotNull";
        }
        if (StringUtils.isBlank(passwd)) {
            flag="passNotNull";
        }
        if("1".equals(WebUtils.getConfigValByName("isValidateCode"))){
            if (StringUtils.isBlank(checkCode)) {
                flag="codeNotNull";
            }
            if (checkCode!=null&&!checkCode.equals((String)req.getSession(true).getAttribute("adminRand"))) {
                flag="codeErro";
            }
        }
        if(!StringUtils.isBlank(flag)){
            result.put("message", INFOMAP.get(flag));
            result.put("status","500");
            result.put("userId", userId);
            return result;
        }

        String validateString = md5.MD5Encode(passwd);
        OperExample operEmp=new OperExample();
        operEmp.or().andNameEqualTo(userId);
        List<Oper> list=operLogic.detail(operEmp);
        Oper oper = null;
        if(list!=null&&list.size()>0){
            oper=list.get(0);
        }
        if (oper != null) {
            flag=operLogic.login(oper,validateString,req,res);
        } else {
            result.put("message", "userNotExist");
            result.put("status","500");
            flag="userNotExist";
        }

        return result;
    }

    public void setCookie(HttpServletResponse res,HttpServletRequest req,String userId,String passwd,String[] isRemberPass,String keepTime){
        if(isRemberPass!=null&&!StringUtils.isBlank(keepTime)){
            String cookieValue=CookieUtils.getCookie(req, "userId");
            if((StringUtils.isBlank(cookieValue))||(!StringUtils.isBlank(cookieValue)&&!keepTime.equals(CookieUtils.getCookie(req, "keepTime")))){
                CookieUtils.addCookie(res, "userId", userId, Integer.parseInt(keepTime), "/", null, null);
                CookieUtils.addCookie(res, "passwd", passwd, Integer.parseInt(keepTime), "/", null, null);
                CookieUtils.addCookie(res, "isRemberPass", isRemberPass[0], Integer.parseInt(keepTime), "/", null, null);
                CookieUtils.addCookie(res, "keepTime", keepTime, Integer.parseInt(keepTime), "/", null, null);
            }
        }else{
            CookieUtils.removeCookie(res, "userId", "/", null);
            CookieUtils.removeCookie(res, "passwd", "/", null);
            CookieUtils.removeCookie(res, "isRemberPass", "/", null);
            CookieUtils.removeCookie(res, "keepTime", "/", null);
        }
    }

    /**
     * @param request
     * @param response
     * @return
     * @throws Exception String 
     * @author wuxuanyi
     * @since 2014-6-30
     * 用户退出登录
     */
    @RequestMapping("/logOut")
    public String Loginout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Oper oper = WebUtils.getOper(request);
        request.getSession().invalidate(); // 设置session失效
        if (oper != null) {
            log.info("用户退出[" + oper.getName() + "]");
            operLogic.saveLog(oper.getName(), "用户退出[" + oper.getName() + "]", request.getRemoteAddr());
        }
        return "redirect:/newsManagement";
    }

    /**
     * 获取随机颜色
     * @param fc
     * @param bc
     * @return Color
     * @author wuxuanyi
     * @since 2014-6-26
     */
    public Color getRandColor(int fc, int bc) {//给定范围获得随机颜色 
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    /**
     * 获取验证码
     */
    @ResponseBody
    @RequestMapping("/ifm/getLoginImage")
    public void getRegisterImage(HttpServletRequest request, HttpServletResponse response) {
        //设置页面不缓存 
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        // 在内存中创建图象 
        int width = 60, height = 20;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // 获取图形上下文 
        Graphics g = image.getGraphics();

        //生成随机类 
        Random random = new Random();

        // 设定背景色 
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);

        //设定字体 
        g.setFont(new Font("Times New Roman", Font.PLAIN, 18));

        //画边框 
        //g.setColor(new Color()); 
        //g.drawRect(0,0,width-1,height-1); 

        // 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到 
        //g.setColor(getRandColor(160,200)); 
        /*
         * for (int i=0;i<155;i++) { int x = random.nextInt(width); int y = random.nextInt(height); int xl = random.nextInt(12); int yl = random.nextInt(12); g.drawLine(x,y,x+xl,y+yl); }
         */

        // 取随机产生的认证码(4位数字) 
        String sRand = "";
        for (int i = 0; i < 4; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
            // 将认证码显示到图象中 
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));//调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成 
            g.drawString(rand, 13 * i + 6, 16);
        }

        // 将认证码存入SESSION 
        request.getSession(true).setAttribute("adminRand", sRand);

        // 图象生效 
        g.dispose();

        // 输出图象到页面 
        try {
            ImageIO.write(image, "JPEG", response.getOutputStream());
        } catch (IOException e) {
            System.out.println("验证码生成错误！");
            e.printStackTrace();
        }
    }

}
