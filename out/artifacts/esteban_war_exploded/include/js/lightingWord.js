var lightingNum=1;
function changeColor(){ 
	var color="#f00|#0f0|#00f|#880|#808|#088|yellow|green|blue|gray";
	color=color.split("|"); 
	alert(lightingNum+"   "+lightingNum*1 % 2);
	if(lightingNum*1 % 2==0){
		$("#blink").text("new~");
		lightingNum=1;
	}else{
		$("#blink").text("new");
		lightingNum=2;
	}
	$("#blink").css("color",color[parseInt(Math.random() * color.length)]);
} 
setInterval("changeColor()",1000); 
