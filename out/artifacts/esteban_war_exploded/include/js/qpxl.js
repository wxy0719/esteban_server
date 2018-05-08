//首页大广告
var gg960ShowTime = 4000; //播放时间
var gg960Time = null;

function open_gg960(showBtn){
    $('.gg_full .gg_fcon').html(gg960Con).slideDown(300,function(){
        if(showBtn !== false){
            $('.gg_full .gg_fbtn').fadeIn();
        }
        if(gg960Time){clearTimeout(gg960Time);}
        gg960Time = setTimeout(close_gg960,gg960ShowTime);
    });
}
function close_gg960(){
    $('.gg_full .gg_fcon').slideUp(500,function(){
        $(this).html('');
        $('.gg_fclose').hide();
        $('.gg_freplay').show();
    });
}
$('.gg_fclose').click(function(){
    if(gg960Time){clearTimeout(gg960Time);}
    close_gg960();
    return false;
});
$('.gg_freplay').click(function(){
    if(gg960Time){clearTimeout(gg960Time);}
    open_gg960(false);
    $('.gg_freplay').hide();
    $('.gg_fclose').show();
    return false;
});

var gg960Con;
var fullAdType = 'swf';
var fullAdUrl = '/'
var fullAdName = 'include/images/qpad.swf';;
if(fullAdName)
{
	if(fullAdType == 'swf')
	{
		gg960Con = '<center><embed wmode="transparent" height="400" width="1000" flashvars="alink1='+fullAdUrl+'" allowscriptaccess="always" quality="high" name="Advertisement" id="Advertisement" style="" src="include/images/qpad.swf" type="application/x-shockwave-flash"></embed></center>';
	}
	else
	{
		gg960Con = '<a href="'+fullAdUrl+'" target="_blank"><img width="1000" height="400" src="include/images/qpad.jpg"/></a>';//flash无法显示时，显示JPG广告
	}
	
	//setTimeout(open_gg960,1500);//延迟显示
}