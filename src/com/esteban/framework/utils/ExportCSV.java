package com.esteban.framework.utils;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * 将数据导出成CSV文件
 *
 * @since 2013-6-21
 */
public class ExportCSV {
    private static Logger log = Logger.getLogger(ExportCSV.class);
    private String exportFilename;
    private HttpServletResponse response;

    /**
     * 构造方法
     *
     * @param response
     */
    public ExportCSV(HttpServletResponse response) {
        this(response, null);
    }

    /**
     * 构造方法
     *
     * @param response
     * @param exportFilename 返回文件名
     */
    public ExportCSV(HttpServletResponse response, String exportFilename) {
        super();
        this.response = response;
        if (StringUtils.isBlank(exportFilename)) {
            this.exportFilename = DateOperator.getOffsetDate(0, DateOperator.DATA_TIME_PERSISTENT) + ".csv";
        } else {
            this.exportFilename = exportFilename;
        }
    }

    /**
     * @return the exportFilename
     */
    public String getExportFilename() {
        return exportFilename == null ? "" : exportFilename.trim();
    }

    /**
     * @param exportFilename the exportFilename to set
     */
    public void setExportFilename(String exportFilename) {
        this.exportFilename = exportFilename;
    }

    /**
     * @author lmq0382
     * @date 2012-12-16
     */
    private void setContentType() {
        response.reset();
        response.setContentType("text/html; charset=GBK");
        response.setHeader("Content-disposition", "attachment;filename= " + this.getExportFilename());
    }

    /**
     * 导出到文件
     *
     * @param <T>
     * @param linkedHashMap key=字段名，value=标题
     * @param list<T>
     * @return boolean
     * @since 2013-6-21
     */
    public <T> boolean writeFromModel(LinkedHashMap<String, String> fields, List<T> list) {
        boolean result = false;
        setContentType();
        PrintWriter write = null;
        try {
            write = response.getWriter();
            if (null != fields) {
                StringBuffer title = new StringBuffer();
                for (String v : fields.values()) {
                    title.append(v).append(",");
                }
                write.write(title.toString().replaceAll("(,$)", ","));
                write.write(13);
                write.write(10);
                write.flush();
            }

            if (null != list && list.size() > 0) {
                int i = 0;
                for (T t : list) {
                    String ret = "";
                    for (String key : fields.keySet()) {
                        Object obj = this.getValue(t, key);
                        ret += (obj == null ? "" : obj) + ",";
                    }
                    write.write(ret.replaceAll("(,$)", ","));
                    write.write(13);
                    write.write(10);
                    if (i != 0 && i % 100 == 0) {
                        write.flush();
                    }
                }
            }
            write.flush();
            result = true;
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
            log.error("导出数据异常：" + e.getMessage());
        } finally {
            try {
                if (write != null) {
                    write.close();
                }
            } catch (Exception ie) {
                ie.printStackTrace();
            }
        }
        return result;
    }

    private <T> Object getMethodReturn(T t, String field) {
        Method[] methods = t.getClass().getMethods();
        Object ret = null;
        for (Method method : methods) {
            if (method.getName().equalsIgnoreCase("get" + field) || method.getName().equalsIgnoreCase("is" + field)) {
                try {
                    ret = method.invoke(t);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        return ret;
    }

    private <T> Object getValue(T t, String field) {
        Object ret = null;
        if (!field.contains(".")) {
            ret = this.getMethodReturn(t, field);
        } else {
            ret = this.getValue(this.getMethodReturn(t, field.substring(0, field.indexOf(".", 0))), field.substring(field.indexOf(".", 0) + 1));
        }
        return ret;
    }

    /**
     * 导出到文件
     *
     * @param fields key=字段名，value=标题
     * @param list Map列表
     * @return boolean
     * @since 2013-6-21
     */
    public <T extends java.util.Map<String, Object>> boolean exportFromListMap(LinkedHashMap<String, String> fields, List<T> list) {
        boolean result = false;
        setContentType();
        PrintWriter write = null;
        try {
            write = response.getWriter();
            if (null != fields) {
                StringBuffer title = new StringBuffer();
                for (String v : fields.values()) {
                    title.append(v).append(",");
                }
                write.write(title.toString().replaceAll("(,$)", ","));
                write.write(13);
                write.write(10);
                write.flush();
            }

            if (null != list && list.size() > 0) {
                int i = 0;
                for (T t : list) {
                    String ret = "";
                    for (String key : fields.keySet()) {
                        String v = String.valueOf(t.get(key.toUpperCase()));
                        v = "null".equalsIgnoreCase(v) ? null : v;
                        ret += StringUtils.trimToEmpty(v) + ",";
                    }
                    write.write(ret.replaceAll("(,$)", ","));
                    write.write(13);
                    write.write(10);
                    if (i != 0 && i % 100 == 0) {
                        write.flush();
                    }
                }
            }
            write.flush();
            result = true;
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
            log.error("导出数据异常：" + e.getMessage());
        } finally {
            try {
                if (write != null) {
                    write.close();
                }
            } catch (Exception ie) {
                ie.printStackTrace();
            }
        }
        return result;
    }
}