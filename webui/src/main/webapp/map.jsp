<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
#canvasmap {
	border: 1px solid black;
	background-image: url("IR_Map.jpg");
	background-size: cover;
}
</style>
</head>
<body>
	<h3>RDI Map of India</h3>
	<canvas id="canvasmap" width="866" height="1094"
		style="border:1px solid #d3d3d3;"> </canvas>

	<!-- FOOTER SECTION END-->
	<!-- REQUIRED SCRIPTS FILES -->
	<!-- CORE JQUERY FILE -->
	<script src="assets/js/jquery-1.11.1.js"></script>
	<!-- REQUIRED BOOTSTRAP SCRIPTS -->
	<script src="assets/js/bootstrap.js"></script>
	<!-- BACKGROUND VIDEO PLUGIN  -->
	<script src="assets/js/jquery.mb.YTPlayer.js"></script>
	<!-- SCROLLING SCRIPTS PLUGIN  -->
	<script src="assets/js/jquery.easing.min.js"></script>
	<!-- CUSTOM SCRIPTS   -->
	<script src="assets/js/custom.js"></script>
	<script>
		function printCircle(color, x, y) {
			var ctx = document.getElementById("canvasmap").getContext("2d");
			//draw a circle
			ctx.beginPath();
			ctx.arc(x / 2, y / 2, 7, 0, Math.PI * 4, true);
			ctx.fillStyle = color;
			ctx.fill();
			ctx.strokeStyle = 'orange';
			ctx.stroke();

		}

		$.ajax({
			type : "GET",
			dataType : "json",
			url : "/webui/ws/rdi/getStations",
			success : function(data) {
				for (var i = 0; i < data.length; i++) {
					//alert(data[i].stationCode+", "+data[i].x+", "+data[i].y);
				getRDI(data[i].stationCode, '2012', data[i].x, data[i].y);
					
						}
				//getRDI(421810, '2011', data[0].x, data[0].y);
				
			}
		});

		function getRDI(stationCode, year, x, y) {
			$.ajax({
				type : "GET",
				dataType : "json",
				url : "/webui/ws/rdi/get/" + stationCode + "/" + year,
				success : function(data) {

					var color;
                    var rdi=data.rdi;
					if (rdi >= 2.0 && (rdi!=10000)) {
						color = '#00008B';
						printCircle(color, x, y);
					} else if (rdi >1.49 && rdi <= 1.99) {
						color = '#0000FF';
						printCircle(color, x, y);
					} else if (rdi >= 1.0 && rdi <= 1.49) {
						color = '#7B68EE';
						printCircle(color, x, y);
					} else if (rdi >= -0.99 && rdi <= 0.99) {
						color = '#228B22';
						printCircle(color, x, y);
					} else if (rdi >= -1.49 && rdi <= 1.0) {
						color = '#FFFF00';
						printCircle(color, x, y);
					} else if (rdi >= -1.99 && rdi <= -1.5) {
						color = '#FF7F50';
						printCircle(color, x, y);
					} else if(rdi<=-2 && (rdi!=10000)){
						color = '#800000';
						printCircle(color, x, y);
					}else if(rdi==10000) {
						color = 'black';
						printCircle(color, x, y);
						}
					else{
						
					}
					
				}
			});
		}
	</script>
</body>
</html>