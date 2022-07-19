<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>:::Everyday 캠프마켓:::</title>
     <meta charset="utf-8">
	 <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
     <link rel="icon" href="resources/assets/img/images/favicon.ico">
     <link rel="shortcut icon" href="resources/assets/img/images/favicon.ico" />
     <link rel="stylesheet" href="resources/assets/css/style.css">
     <link rel="stylesheet" href="resources/assets/css/login.css">
     <link rel="stylesheet" href="resources/assets/css/popup.css">
     <link rel="stylesheet" href="resources/assets/css/slider.css">
	 <link rel="stylesheet" href="resources/assets/css/zerogrid.css" type="text/css" media="screen">
	<link rel="stylesheet" href="resources/assets/css/responsive.css" type="text/css" media="screen"> 
     <script src="resources/assets/js/jquery.js"></script>
     <script src="resources/assets/js/jquery-migrate-1.1.1.js"></script>
     <script src="resources/assets/js/superfish.js"></script>
     <script src="resources/assets/js/jquery.easing.1.3.js"></script>
     <script src="resources/assets/js/sForm.js"></script>
     <script src="resources/assets/js/jquery.carouFredSel-6.1.0-packed.js"></script>
     <script src="resources/assets/js/tms-0.4.1.js"></script>
	 <script src="resources/assets/js/css3-mediaqueries.js"></script>
	<script src="resources/assets/js/httpRequest.js"></script>
	<script src="resources/assets/js/jquery-3.6.0.min.js"></script>
		 <script src="resources/assets/js/menuBar.js"></script>

</head>

<body style="background-color: #5fa022;">
<div class="main">
<!-- ==============================header================================= -->
  <header> 
  <div class="zerogrid">
<!--   <div class="zerogrid" style="height:70px"> -->
    <div class="col-full">
	<div class="wrap-col">
    <h1><a href="home"><img src="resources/assets/img/images/logo.png" alt="EXTERIOR"></a> </h1>
    
           	<div class="menu_block">
						<nav>
						<input type="hidden" id=checkLogin value=${empty sessionScope.loginId}>
            		<ul class="sf-menu">
                   <li><a href="home">Home</a></li>
                   
                   <li><a href="marketMain_Temp">밀키트</a></li>
                   <li><a href="reviewMain_Temp">상품후기</a></li>
                   <li><a href="campingAreaMain_Temp">주변캠핑장 </a></li>
                   <li class="with_ul" id="memberMenu"><a href="#">마이페이지</a>
				   <ul>
                         <li><a href="money.do">카드등록</a></li>
                         <li><a href="money.do">카드충전</a></li>
                         <li><a href="#"
                         	onclick="goMyBasket(${sessionScope.loginId eq null})">
                         	장바구니</a></li>
                   </ul>
				   </li>
                   <li class="current" id="loginTag"><a href="login_Temp">로그인</a></li>
                   <li id="logoutTag"><a href="logout">로그아웃</a></li>
                 </ul>
           </nav>
           <div class="clear"></div>
		</div>
      </div>
    </div>
    </div>
</header>
<!-- =======content================================ -->

<div class="content">
  <div class="zerogrid" style="margin:80px 300px 60px 300px;">
    <!-- <div class="col-full">
      <h2></h2>
    </div> -->
    <div class="clear"></div>	
	<div class="row">
    <div class="portfolio">  
    
    
      <main  style="display: flex; align-items: center;justify-content: center;
   	height: 65vh; width: 100%;
    background: url('resources/assets/img/images/bbq.jpg') no-repeat center center;
    background-size: cover;">
    <div class="main2" style="height:470px; margin:250px auto; text-align:center;">
    
       <!-- 로그인 통과하면 상품페이지로 이동 -->
       		 <form id="form" name="form" class="form_class" 
       		 action="marketMain_Temp" method="post" style="display:none;margin-top:53px"> 
  			<!-- <form id="form" name="form" class="form_class" action="money.do" method="post" style="display:none;"> --> 
            <div class="form_div">
                <label>아이디</label>
                <input class="field_class" name="id" id="id" type="text" autofocus><br>
                <label>비밀번호</label>
                <input id="pwd" class="field_class" name="pwd" type="password">
            </div>
            <div class="info_div" style="display:flex;">        
  			<a class="info_div popup-btn" href="#">아이디 찾기</a>                    
  			<a class="info_div popup-btn1" href="#">비밀번호 찾기</a>
            </div>  
            <div class="info_div">          
                <input class="submit_class" type="button" form="form" value="로그인"
                onclick="goLogin()">
            </div>
            <div class="info_div" style="margin-top:10px;">
               	<p style="color: #FFB5C8">아직 계정이 없으신가요?
               	<a style="border:none; box-shadow:none; background:none;
               	text-decoration:underline; width:200px; margin: 10px auto;" href="memberCheck.do">
               	회원가입하러 가기</a></p>
            </div>
        </form>
	</div>
</main> 

  
	<!-- 아이디찾기 팝업 -->
      <div class="popup-wrap">
    <div class="popup-box">
      <form id="popForm" name="popForm" class="form_class" method="post">
            <div class="form_div">
                <label>이름</label>
                <input class="field_class" name="popName" id="popName" type="text" autofocus><br>
                 <label>생년월일</label>
                <input class="field_class" name="popBirth" id="popBirth" placeholder="예)990101" type="text"><br>
                <label>이메일</label>
                <input class="field_class" name="popEmail" type="text" id="popEmail" placeholder="예)abc@def.com">
            </div>
            <div class="info_div" id="idDiv">          
                <input class="submit_class" type="button" form="form" value="아이디 찾기"
                onclick="searchID()">
            </div>
            <div  class="info_div" style="display:flex; display:none; margin:20 auto" id="idText">
				<label>아이디 :&emsp;<span id="searchId" style="color:#e2827e; font-size:20px"></span></label>&emsp;		
			</div>          
		      <div style="margin-top:15px">
		      	<a class="close-btn popup-close" href="#">로그인 화면으로 이동</a>
		      </div>
        </form>
    </div>
  </div>  
  
  
  <!-- 비밀번호 찾기 팝업 -->
    <div class="popup-wrap1">
    <div class="popup-box1">
      <form id="popPwdForm" name="popPwdForm" class="form_class" method="post">
       	<div class="form_div">
            <label>아이디</label>
            <input class="field_class" name="popPwdId" id="popPwdId" type="text" autofocus><br>
            <label>이름</label>
            <input class="field_class" name="popPwdName" id="popPwdName" type="text"><br>
            <label>이메일</label>
            <input class="field_class" name="popPwdEmail" type="text" id="popPwdEmail" placeholder="예)abc@def.com">
       	</div>
       	<div class="info_div" id="pwdDiv">          
                <input class="submit_class" type="button" form="form" value="비밀번호 찾기"
                onclick="searchPwd()">
        </div>
         <div  class="info_div" style="display:flex; display:none; margin:20 auto" id="pwdDiv2">
         	<div class="form_div">
				<label>새로운 비밀번호</label>
	            <input class="field_class" name="newPwd" id="newPwd" type="password" autofocus><br>
				<label>비밀번호 확인</label>
	            <input class="field_class" name="newPwd2" id="newPwd2" type="password"><br>           
       		</div>
       		<div class="info_div" id="pwdDiv3">          
                <input class="submit_class" type="button" form="form" value="비밀번호 변경하기"
                onclick="changePwd()">
        	</div>
        	<input type="hidden" id="idx">
		</div>          
	    <div style="margin-top:15px">
	      	<a class="close-btn popup-close1" href="##">로그인 화면으로 이동</a>
	    </div>				 
	</form>
    </div>
  </div>     
        
    </div>
    </div>
   
    <div class="row">
    <div class="bottom_block">
      
     
      </div>
	  </div>
    </div>
  </div>
</div>
<!-- ==============================footer================================= -->

<footer style="padding: 0px;">    
  <div class="zerogrid">
    <div class="col-full">
		<div class="wrap-col">
     Gourmet © 2013  &nbsp;&nbsp; |&nbsp;&nbsp;  
      <a href="#">Privacy Policy</a>    
      &nbsp;&nbsp;|&nbsp;&nbsp;  Designed by 
      <a href="http://www.templatemonster.com/" rel="nofollow">TemplateMonster</a>
       - <a href="https://www.zerotheme.com/" title="free website templates">ZEROTHEME</a>
	 	</div>
    </div>
  </div>
</footer>
</body>

<script>

	$(document).ready(function(){
       	$('#form').fadeIn(1000);     
   });	
   
	//아이디찾기 팝업
	$('.popup-btn').click(function(e) {
    $('.popup-wrap').fadeIn(500);
    $('.popup-box').removeClass('transform-out').addClass('transform-in');

    e.preventDefault();
  });

  $('.popup-close').click(function(e) {
    $('.popup-wrap').fadeOut(500);
    $('.popup-box').removeClass('transform-in').addClass('transform-out');

    e.preventDefault();
  });
  
  //비밀번호 찾기 팝업
  $('.popup-btn1').click(function(e) {
	    $('.popup-wrap1').fadeIn(500);
	    $('.popup-box1').removeClass('transform-out1').addClass('transform-in1');

	    e.preventDefault();
	  });

	  $('.popup-close1').click(function(e) {
	    $('.popup-wrap1').fadeOut(500);
	    $('.popup-box1').removeClass('transform-in1').addClass('transform-out1');

	    e.preventDefault();
	  });
	
	//로그인하기
	function goLogin(){
		var id = document.getElementById("id");
		var idVal = id.value.trim();
		var pwd = document.getElementById("pwd");
		var pwdVal = pwd.value.trim();
		//유효성 검사
		if(idVal==''){
			alert("아이디를 입력해 주세요");
			id.focus();
			//input(text, pw)태그에 required속성을 붙여주면 빈칸이면 false반환  
			return false;
		}
		
		if(pwdVal==''){
			alert("비밀번호를 입력해주세요");
			pwd.focus();
			return false;
		}
		
		var url = "goLogin.do";
		var param = "id=" + idVal + "&pwd=" + pwdVal;
		sendRequest(url, param, cb, "POST");
	}
	
	function cb(){	
		if(xhr.readyState==4 && xhr.status==200){
			var data = xhr.responseText;
			var json = (new Function('return'+data))();			
			
			if(json[0].param==0){
				alert("아이디나 비밀번호를 확인해 주세요");
				var id = document.getElementById("id");
				id.focus();
				id.value = "";
				document.getElementById("pwd").value="";
				
			}else{
				var name = json[1].name;
				console.log(name);
				alert("환영합니다 "+name+" 회원님~");				
				//var form = document.form;			
				document.form.submit();					
			}
		}
	}	
	
	//아이디찾기
	function searchID(){
		var form = document.popForm;
		var name = form.popName;
		var birth = form.popBirth;
		var email = form.popEmail;
		var nameVal = name.value.trim();
		var birthVal = birth.value.trim();
		var emailVal = email.value.trim();
		
		if(nameVal==''){
			alert("이름을 입력해 주세요");
			name.focus();
			return;
		}
		if(birthVal==''){
			alert("생년월일을 입력해 주세요");
			birth.focus();		
			return;
		}
		if(emailVal==''){
			alert("이메일을 입력해 주세요");
			email.focus();
			return;
		}
		
		var url = "searchID.do";
		var param = "name="+nameVal+"&birth="+birthVal+"&email="+emailVal;
		sendRequest(url, param, cb1, "POST");
	}
	
	function cb1(){
		if(xhr.readyState==4 && xhr.status==200){
			var data = xhr.responseText;
			var json = (new Function('return'+data))();
			//회원정보가 없다면
			if(json[0].id == 'none'){
				alert("입력하신 정보와 일치하는 아이디가 존재하지 않습니다. 다시 시도해 주세요");
				$('#popName').val('');
				birth.value="";
				email.value="";
				name.focus();
				return;
			}else{
				$('#idDiv').hide();
				document.getElementById('idText').style.display = 'block';
				document.getElementById('searchId').innerHTML = json[0].id;
			}
		}
	}	
	
	//비밀번호 찾기
	function searchPwd(){
		var form = document.popPwdForm;
		var name = form.popPwdName;
		var id = form.popPwdId;
		var email = form.popPwdEmail;
		var nameVal = name.value.trim();
		var idVal = id.value.trim();
		var emailVal = email.value.trim();
		
		if(nameVal==''){
			alert("이름을 입력해 주세요");
			name.focus();
			return;
		}
		if(idVal==''){
			alert("아이디를 입력해 주세요");
			id.focus();		
			return;
		}
		if(emailVal==''){
			alert("이메일을 입력해 주세요");
			email.focus();
			return;
		}
		
		var url = "searchPwd.do";
		var param = "name="+nameVal+"&id="+idVal+"&email="+emailVal;
		sendRequest(url, param, cb2, "POST");
	}
	
	function cb2(){
		if(xhr.readyState==4 && xhr.status==200){
			var data = xhr.responseText;
			var json = (new Function('return'+data))();
			//회원정보가 없다면
			if(json[0].param == 0){
				alert("일치하는 정보가 없습니다. 다시 시도해 주세요");
				$('#popPwdName').val('');
				id.value="";
				email.value="";
				id.focus();
				return;
			}else{//회원정보가 있다면	
				$('#pwdDiv').hide();
				document.getElementById('pwdDiv2').style.display = 'block';
				document.getElementById('idx').value= json[0].idx;
				console.log(document.getElementById('idx').value);
			}
		}
	}
	
	//비밀번호 변경하기
	function changePwd() {
		var pwdPattern = /^(?=.*[a-zA-Z])(?=.*[0-9]).{8,15}$/;
		var pwd = document.getElementById("newPwd");
		var pwd2 = document.getElementById("newPwd2");
		var pwdVal = pwd.value.trim();
		var pwd2Val = pwd2.value.trim();
		
		if(pwdVal==''){
			alert("비밀번호를 입력해 주세요");
			return;
		}
		if(!pwdPattern.test(pwdVal)){//정규표현식 형식과 일치하지 않을 때
			alert("8~15자리의 영문과 숫자를 모두 사용해 입력해야 합니다(특수문자 제외)");
			pwd.value="";
			pwd.focus();
			return;
		}else{//비밀번호 형식 통과 후
			if(pwdVal!=pwd2Val){//비밀번호 불일치시 
				alert("비밀번호가 일치하지 않습니다. 다시 시도해 주세요");
				pwd.value="";
				pwd2.value="";
				pwd.focus();
				return;
			}else{//비밀번호 일치시
				var idx = document.getElementById('idx').value; 
				url = "changePwd.do";
				param = "pwd="+pwdVal+ "&idx="+idx;
				console.log("pwd: "+pwdVal+"/idx: "+idx);
				sendRequest(url, param, cb3, "POST");
			}
		}		
	}
	
	function cb3(){
		if(xhr.readyState==4 && xhr.status==200){
			var data = xhr.responseText;
			var json = (new Function('return'+data))();
			if(json[0].param=='y'){
				alert("비밀번호가 정상적으로 변경되었습니다");
				$('#pwd11').attr('disabled', true);
				$('#pwd22').attr('disabled', true);
			}else{
				alert("비밀번호가 변경되지 못했습니다. 다시 시도해주세요");
				return;
			}
		}		
		
	}
</script>
</html>