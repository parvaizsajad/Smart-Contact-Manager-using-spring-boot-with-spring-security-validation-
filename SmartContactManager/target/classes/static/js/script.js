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
}

// function for the search contact
const search = () =>{

		let querry=$("#search-input").val();

		if(querry==""){
			
			$(".search-result").hide();
		}else{
			let url=`http://localhost:8080/search/${querry}`;
		

			fetch(url)
			.then((response)=>{
				return response.json();
			})
			.then((data)=>{
				console.log(data)

				let text=`<div class='list-group'>`
				data.forEach((contact) => {
					text+=`<a href='/user/${contact.id}/show-contact' class='list-group-item list-group-item-action'> ${contact.name} </a>`
				});



				text+=`</div>`; 
				$(".search-result").html(text);
				$(".search-result").show();
			});
console.log(querry);


		}
	};
	



