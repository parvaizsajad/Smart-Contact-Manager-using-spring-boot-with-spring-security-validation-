console.log("hello from js file")
const toggleSidebar = () =>{
	if($(".sidebar").is(":visible")){
		$(".sidebar").css("display","none");
		console.log("hi from js")
		$(".content").css("margin-left","02%")
	}else{
		$(".sidebar").css("display","block");
		$(".content").css("margin-left","20%")
	}
	
};


