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
	
// request to create the javascript order

let paymentStart=()=>{
	console.log("payment started");
	let paymentField=$('#pay').val();

	console.log(paymentField);
	if(paymentField==""||paymentField==undefined||paymentField==null){
		alert("please enter the amount");
		return;
	}
	$.ajax(
		{
			url:'/user/createPayment',
			data:JSON.stringify({amount:paymentField,info:'payment_request'}),
			contentType:'application/json',
			type:'POST',
			dataType:'json',
			success:function(response){
				console.log(response);
				if(response.status=='created'){
					
					var options = {
    "key": "rzp_test_QxNOgAQ8FjC42V", // Enter the Key ID generated from the Dashboard
    "amount": response.amount, // Amount is in currency subunits. Default currency is INR. Hence, 50000 refers to 50000 paise
    "currency": "INR",
    "name": "Smart Contact Manager",
    "description": "Donation towards smart contact manager",
    "image": "https://static.remove.bg/remove-bg-web/94d2aee5eb04f99c74eb8c98ee22ef29d449bc28/assets/start_remove-c851bdf8d3127a24e2d137a55b1b427378cd17385b01aec6e59d5d4b5f39d2ec.png",
    "order_id": response.id, //This is a sample Order ID. Pass the `id` obtained in the response of Step 1
    "handler": function (response){
        console.log(response.razorpay_payment_id);
        console.log(response.razorpay_order_id);
        console.log(response.razorpay_signature)
//alert("payment successfull")


updatePaymentOnServer(response.razorpay_payment_id,response.razorpay_order_id,"paid")
    },
    "prefill": {
        "name": "",
        "email": "",
        "contact": ""
    },
    "notes": {
        "address": "Parvaiz sajad"
    },
    "theme": {
        "color": "#3399cc"
    }
};
let rzp=new Razorpay(options)
rzp.on('payment.failed', function (response){
        console.log(response.error.code);
        console.log(response.error.description);
        console.log(response.error.source);
        console.log(response.error.step);
        console.log(response.error.reason);
        console.log(response.error.metadata.order_id);
        console.log(response.error.metadata.payment_id);
		//alert("oops payment failed")
		swal("Sorry!", "oops payment failed!", "error");
});
rzp.open()
				}
			},
			error:function(error){
				console.log(error);
				alert("something went wrong")
			}
		}
	)
};
function updatePaymentOnServer(payment_id,order_id,status)
{
	$.ajax(
		{
			url:'/user/update_order',
			data:JSON.stringify({payment_id:payment_id,
			order_id:order_id,
			status:status}),
			contentType:'application/json',
			type:'POST',
			dataType:'json',
			success:function(response){
				swal("Congrats!", "payment successfull!", "success");
				
			},
			error:function(error){
						swal("Sorry!", " payment not saved on server(incomplete)!", "error");
			}
			
			})
}
	


