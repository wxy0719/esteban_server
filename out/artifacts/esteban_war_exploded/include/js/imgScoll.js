  var focusScroll_01 = new ScrollPic();
  focusScroll_01.scrollContId   = "FS_Cont_01"; //内容容器ID
  focusScroll_01.arrLeftId      = "FS_arr_left_01";//左箭头ID
  focusScroll_01.arrRightId     = "FS_arr_right_01"; //右箭头ID

  focusScroll_01.dotListId      = "FS_numList_01";//点列表ID
  focusScroll_01.dotClassName   = "";//点className
  focusScroll_01.dotOnClassName	= "selected";//当前点className
  focusScroll_01.listType		= "number";//列表类型(number:数字，其它为空)
  focusScroll_01.listEvent      = "onmouseover"; //切换事件

  focusScroll_01.frameWidth     = 944;//显示框宽度
  focusScroll_01.pageWidth      = 944; //翻页宽度
  focusScroll_01.upright        = false; //垂直滚动
  focusScroll_01.speed          = 10; //移动速度(单位毫秒，越小越快)
  focusScroll_01.space          = 50; //每次移动像素(单位px，越大越快)
  focusScroll_01.autoPlay       = true; //自动播放
  focusScroll_01.autoPlayTime   = 5; //自动播放间隔时间(秒)
  focusScroll_01.circularly     = true;
  focusScroll_01.initialize(); //初始化
  focusScroll_01.onpagechange = function(){
   $(".scroll_info").hide();
   $("#txt0"+(focusScroll_01.pageIndex+1)).show();
  };
