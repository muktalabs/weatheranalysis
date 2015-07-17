<html>
<head>

<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1" />
<meta name="description" content="" />
<meta name="author" content="" />
<!--[if IE]>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <![endif]-->
<title>Free bootstrap corporate template</title>
<!-- BOOTSTRAP STYLE SHEET -->
<link href="assets/css/bootstrap.css" rel="stylesheet" />
<!-- FONT AWESOME ICONS STYLE SHEET -->
<link href="assets/css/font-awesome.css" rel="stylesheet" />
<!-- CUSTOM STYLES -->
<link href="assets/css/style.css" rel="stylesheet" />
<!-- HTML5 Shiv and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>
<style>
table {
	width: 100%;
	left: 400px;
	top: -50px;
	position: absolute;
}

table, th, td {
	border: 1px solid black;
	border-collapse: collapse;
}

th, td {
	padding: 5px;
	text-align: left;
}

table#t01 tr:nth-child(even) {
	background-color: #eee;
}

table#t01 tr:nth-child(odd) {
	background-color: #fff;
}

table#t01 th {
	background-color: black;
	color: white;
}
</style>

<body>
	<div class="navbar navbar-default navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>

			</div>
			<div class="navbar-collapse collapse navbar-right scroll-me">
				<ul class="nav navbar-nav ">
					<li><a href="#home">HOME</a></li>
					<li><a href="#services">WHAT's RDI</a></li>
					<li><a href="#works">SERVICES</a></li>
					<li><a href="#contact">CONTACT</a></li>
				</ul>
			</div>

		</div>
	</div>
	<!-- NAVBAR CODE END -->
	<div id="home">
		<div class="overlay">
			<!-- overylay class usage -->
			<div class="container">
				<div class="col-md-8 col-md-offset-2 text-center">

					<h1>WEATHER ANALYSIS</h1>
					<h2>Analyse it ,Improvise it</h2>
					<p class="p-cls">Lorem ipsum dolor sit amet, consectetur
						adipiscing elit.</p>
				</div>

			</div>
		</div>

	</div>
	<!--HOME SECTION END  -->
	<section id="services">
		<div class="container">
			<div class="row text-center pad-bottom">
				<div class="col-md-6 col-md-offset-3 col-sm-6 col-sm-offset-3">
					<h2 class="head1">What's RDI ?</h2>
					<p>Reconnaisance Drought Index(RDI) is one of the most
						effective parameter used to predict the chances of drought.It
						binds the severity of drought to a number. On the basis of
						obtained RDI , we can have a future insight of drought probablity.
						RDI is calculated using parameters like evapotranspiration ,
						precipitaion and temperature .</p>
				</div>
			</div>
			<div class="row text-center">

				<div class="col-md-4 col-sm-4 col-xs-12">


					<table id="t01">
						<tr>
							<th>S.No</th>
							<th>RDI value</th>
							<th>Category</th>
						</tr>
						<tr>
							<td>1</td>
							<td>2.0 and above</td>
							<td>Extreme Wet Condition</td>
						</tr>
						<tr>
							<td>2</td>
							<td>1.55 to 1.99</td>
							<td>Very Wet Condition</td>
						</tr>
						<tr>
							<td>3</td>
							<td>1.0 to 1.49</td>
							<td>Moderately Wet Condition</td>
						</tr>
						<tr>
							<td>4</td>
							<td>-0.99 to 0.99</td>
							<td>Near Normal Condition</td>
						</tr>
						<tr>
							<td>5</td>
							<td>-1.0 to -1.49</td>
							<td>Moderately Dry Condition</td>
						</tr>
						<tr>
							<td>6</td>
							<td>-1.5 to -1.99</td>
							<td>Severely Dry Condition</td>
						</tr>
						<tr>
							<td>7</td>
							<td>-2.0 and Less</td>
							<td>Extreme Dry Condition</td>
						</tr>
					</table>


				</div>
			</div>
	</section>
	<!-- CLIENTS SECTION END-->
	<footer>
		© 2015 YourDomain.com | <a href="http://www.designbootstrap.com/"
			target="_blank">by DesignBootstrap</a>
	</footer>
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

</body>

</html>
