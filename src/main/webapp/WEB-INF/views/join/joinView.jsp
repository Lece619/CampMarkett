<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<title>:::Everyday 캠프마켓:::</title>
     <link rel="icon" href="images/favicon.ico">
     <link rel="shortcut icon" href="images/favicon.ico" />
     <link rel="stylesheet" href="resources/assets/css/style.css">
     <link rel="stylesheet" href="resources/assets/css/login.css">
    <link rel="stylesheet" href="resources/assets/css/popup.css">
	 <link rel="stylesheet" href="resources/assets/css/zerogrid.css" type="text/css" media="screen">
	 <link rel="stylesheet" href="resources/assets/css/responsive.css" type="text/css" media="screen"> 
     <script src="resources/assets/js/jquery.js"></script>
     <script src="resources/assets/js/jquery-migrate-1.1.1.js"></script>
     <script src="resources/assets/js/superfish.js"></script>
     <script src="resources/assets/js/jquery.easing.1.3.js"></script>
     <script src="resources/assets/js/sForm.js"></script>
	 <script src="resources/assets/js/css3-mediaqueries.js"></script>
	<script src="resources/assets/js/jquery-3.6.0.min.js"></script>
	<script src="resources/assets/js/httpRequest.js"></script>
	<script src="resources/assets/js/jquery-3.6.0.min.js"></script>
		 <script src="resources/assets/js/menuBar.js"></script>

</head>
<body>
<div class="main">
<!--==============================header=================================-->
 <header> 
  <div class="zerogrid">
				<div class="col-full">
					<div class="wrap-col">
						<h1>
							<a href="home"><img
								src="resources/assets/img/images/logo.png" alt="EXTERIOR"></a>
						</h1>

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
                         <li><a href="myInfo.do">내정보수정</a></li>
                         <li><a href="money.do">카드등록</a></li>
                         <li><a href="money.do">카드충전</a></li>
                         <li><a href="#"
                         	onclick="goMyBasket(${sessionScope.loginId eq null})">
                         	장바구니</a></li>
                   </ul>
				   </li>
                   <li id="loginTag" class="current"><a href="login_Temp">로그인</a></li>
                   <li id="logoutTag"><a href="logout">로그아웃</a></li>
                 </ul>
           </nav>					
							<div class="clear"></div>
						</div>
						<div class="clear"></div>
					</div>
				</div>
			</div>
</header>
<!--=======content================================-->

<div class="content">
  <div class="zerogrid">
  	<div class="row">
	<form action="joinView.do" name="form" style="width:75%; margin:auto">
	<div class="row"><br><br><br>
	<p style="color: #79302b; font-size: 25px">기본정보 입력</p>	
	</div>	
	
	

	
	<div class="form_div" style="color: #79302b; font-weight:500; margin-top: 15px">
        <label class="join_label">아이디<span style="color:red">(필수)</span></label><br>		
        <input class="field_class_join" name="id" id="id" type="text" autofocus
        placeholder="영문자로 시작하는 5~15자리의 영문자와 숫자만 입력이 가능합니다">
        <a class="btn_join" id="idCheckBtn">중복확인</a><br>	
               
       	<label class="join_label">비밀번호<span style="color:red">(필수)</span></label><br>       
        <input class="field_class_join" name="pwd" id="password1" type="password"
        placeholder="8~15자리의 영문과 숫자를 모두 사용해 입력해야 합니다(특수문자 제외)">
		<span class="span_join" id="pwdErrorMsg" style="color:red; margin-top: 10px">올바른 형식이 아닙니다</span><br>
		
		
		<label class="join_label">비밀번호 확인<span style="color:red">(필수)</span></label><br>		
        <input class="field_class_join" name="pwd2" id="password2" type="password"
        placeholder="8~15자리의 영문과 숫자를 모두 사용해 입력해야 합니다(특수문자 제외)">
		<span class="span_join" id="equalMsg" style="color:#699440">비밀번호가 일치합니다</span>
		<span class="span_join" id="notEqualMsg" style="color:red">비밀번호 불일치!</span><br>
       
       	<div class="row"><br><br><br>
		<p style="color: #79302b; font-size: 25px; margin-bottom: 30px">개인정보 입력</p>	
		</div>	
       
       	<label class="join_label">이름<span style="color:red">(필수)</span></label><br>		
	    <input class="field_class_join" type="text" name="name" id="name" value="${sessionScope.wannaName}">
		<!-- 성별 -->
		<input type="checkbox" name="gender" value="남자" onclick="chooseGender(this)" checked>남자
		<input type="checkbox" name="gender" value="여자" onclick="chooseGender(this)">여자&emsp;
		<span id=nameErrorMsg style="color:red">이름은 한글만 입력이 가능합니다</span><br>
       
       	<label class="join_label">생년월일<span style="color:red">(필수)</span></label><br>		
        <input class="field_class_join" name="birth" id="birth" type="text"
        	placeholder="6자리로 입력해주세요 (예) 990101)">
		<span id="birthErrorMsg" style="color:red">올바른 형식이 아닙니다</span><br>
        
        <label class="join_label">이메일<span style="color:red">(필수)</span></label>&emsp;
        <span style="color:#699440">비밀번호 초기화 메일 수신 등에 반드시 필요한 정보이므로 정확히 입력해주세요.</span><br>		
        <input class="field_class_join" name="email" id="email" type="text" value="${sessionScope.wannaEmail}"
        placeholder="영문자로 시작하는 5~15자리의 영문자와 숫자만 입력이 가능합니다" style="margin-bottom:5px">
        <a class="btn_join" onclick="verifyEmail()">본인인증</a><br>
		<span id="emailErrorMsg" style="color:red">올바른 형식이 아닙니다</span><br>
		
        
        <label class="join_label">전화번호</label><br>		
        <input class="field_class_join" name="hometel" id="hometel" type="text"
        placeholder="예)032-111-1111">
		<span id="hometelErrorMsg" style="color:red">올바른 형식이 아닙니다</span><br>
       
        <label class="join_label">휴대전화번호<span style="color:red">(필수)</span></label><br>		
        <input class="field_class_join" name="mobiletel" id="mobiletel" type="text"
        placeholder="예) 010-2345-6789">
        <a class="btn_join" onclick="certifyTel()">본인인증</a><br>
        
		<label class="join_label">주소<span style="color:red">(필수)</span></label><br>		
        <input class="field_class_join" name="addr" id="address" type="text">
        <a class="btn_join" id="addressBtn" onclick="goPopup()">주소찾기</a><br>
        
        <div style="margin-top: 5px;
    margin-bottom: 50px;
    height: 40px;">
        	<input class="submit_class" type="button" value="취소하기" onclick="location.href='marketMain_Temp'"
        	style="margin:10px 50px 10px 140px; display:inline; height:50px; font-size:20px">
        	<input class="submit_class" type="button" value="가입하기" onclick="join()"
        	style="margin:10px 60px 10px 50px; display:inline; height:50px; font-size:20px">
        <!-- 	<div class="info_div" style="margin-top: 25px; width:620px"> 
        		<input class="submit_class" type="button" form="form" value="취소하기"
               	 	onclick="location.href='marketMain_Temp'" style="margin-right:0px">  
            	<input class="submit_class" type="button" form="form" value="가입하기"
                	onclick="join()" style="margin:0px; display:float">       			
        	</div> -->
        </div>     
    </div>	
</form>   
    </div>
  </div>
</div>
</div>
<!--==============================footer=================================-->

<footer>    
  <div class="zerogrid">
    <div class="col-full">
		<div class="wrap-col">
     koreaIt ② academy  &nbsp;&nbsp; |&nbsp;&nbsp;   <a href="#">Privacy Policy</a>    &nbsp;&nbsp;|&nbsp;&nbsp;  Designed by <a href="https://github.com/SpringProjectKoreaIT/CampMarkett" rel="nofollow">CampMarketTeam</a> - <a href="https://github.com/SpringProjectKoreaIT/CampMarkett" title="free website templates">Group '2' </a>
	 	</div>
    </div>
  </div>
</footer>
</body>
<script>	
$(document).ready(function(){
   	$('#form').fadeIn(500);
});
	//주소찾기 API
	function goPopup(){
		var pop = window.open("jusoPopup.do","pop","width=570,height=420, 
				scrollbars=yes, resizable=no"); 		
	}
	//주소찾기 API cb
	function jusoCallBack(roadFullAddr){
		// 팝업페이지에서 주소입력한 정보를 받아서, 현 페이지에 정보를 등록합니다.
		var address = document.querySelector("#address");
		address.value = roadFullAddr;
		document.getElementById("addressBtn").disabled = true;
	}	
		
	//성별 하나만 체크하기
	function chooseGender(target){
	    var gender = document.getElementsByName("gender");
	    for(var i=0; i<gender.length; i++){
	        if(gender[i] != target){
	        	gender[i].checked = false;
	        }
	    }
	}
	
	//휴대전화인증
	function certifyTel(){
		//유효성 체크
		var phonePattern = /^01([0:1:6:7:8:9]?)-([0-9]{3,4})-([0-9]{4})$/;
		var tel = document.getElementById("mobiletel");
		var mobiletel = tel.value.trim();
		
		if(mobiletel == ''){
			alert("전화번호를 입력해 주세요");
			return;
		}
		if(!phonePattern.test(mobiletel)){
			alert("잘못된 형식의 전화번호입니다");
			tel.value='';
			tel.focus();
			return;
		}
	}
		
	
	//가입하기
	function join(){
		var f = document.form;
		var id = f.id.value;
		var pwd = f.pwd.value;
		var name = f.name.value;
		//체크박스 value받기
		var gender = document.querySelector('input[name="gender"]:checked').value;
		var birth = f.birth.value;
		var hometel = f.hometel.value;
		var mobiletel = f.mobiletel.value;
		var email = f.email.value;
		var addr = f.addr.value;	

	
		if(name==''){
			alert("이름을 입력해주세요");
			f.name.focus();
			return;
		}
		
		if(birth==''){
			alert("생년월일을 입력해주세요");
			f.birth.focus();
			return;
		}
		
		if(pwd==''){
			alert("비밀번호를 입력해주세요");
			f.pwd.focus();
			return;
		}
		
		if(email==''){
			alert("이메일을 입력해주세요");
			f.email.focus();
			return;
		}
		
		if(hometel==''){
			hometel="none";
		}
		
		if(addr==''){
			alert("주소를 입력해주세요");
			f.addr.focus();
			return;
		}
			
		var url = "join.do";
		var param = "id="+id+"&pwd="+pwd+"&name="+name+"&gender="+gender+"&birth="
		+birth+"&email="+email+"&hometel="+hometel+"&mobiletel="+mobiletel+"&addr="+addr;
		
		console.log(param);
		sendRequest(url, param, cb, "POST");
	}
	
	function cb(){
		if(xhr.readyState==4 && xhr.status==200){
			var data = xhr.responseText;
			var json = (new Function('return'+data))(); 
			if(json[0].param = 'y'){
				alert("회원가입이 완료되었습니다. 로그인 페이지로 이동합니다.");
				location.href = "login_Temp";
			}else{
				alert("프로그램 상의 문제가 발생했습니다. 관리자에게 문의해 주세요");
				location.href= "관리자 항의 메일 보내기";
			}
		}
	}	

//페이지 로딩되면 자동커서
	$(function(){
		$('#id').focus();
	});
	
	var pwd1 = $('#password1');
	var pwd2 = $('#password2');
	var pwdPattern = /^(?=.*[a-zA-Z])(?=.*[0-9]).{8,15}$/;
	var equalMsg = $('#equalMsg');
	var notEqualMsg = $('#notEqualMsg');
	var pwdErrorMsg = $('#pwdErrorMsg');
	
	//비밀번호 유효성 체크
	pwdErrorMsg.hide();
	
	pwd1.on('input', function(){
		var pwdVal = $.trim(pwd1.val());
		if(!pwdPattern.test(pwdVal)){
			pwdErrorMsg.show();
			if(pwd1.val()==''){
				pwdErrorMsg.hide();
			}
		}else{//이부분을 적어줘야 올바른 형식일 때 에러메시지 지워짐
			pwdErrorMsg.hide();
		}
	});
	
	
	//비밀번호1, 비밀번호2 일치/불일치
	equalMsg.hide();
	notEqualMsg.hide();
	
	pwd2.on('input', function(){
		var pwd1Val = $.trim(pwd1.val());
		var pwd2Val = $.trim(pwd2.val());
	
		if(pwd1Val==pwd2Val){
			equalMsg.show();
			notEqualMsg.hide();
		}else{
			equalMsg.hide();
			notEqualMsg.show();
			if(pwd2.val()==''){
				notEqualMsg.hide();
				equalMsg.hide();
			}
		}		
	});	
	
	//이메일 유효성 체크
	var emailErrorMsg = $('#emailErrorMsg');
	var email = $('#email');
	var emailPattern =
		 /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
	
	emailErrorMsg.hide();
		 
	email.on('input', function(){
		var emailVal = $.trim(email.val());
		if(!emailPattern.test(emailVal)){
			emailErrorMsg.show();
		}else{
			emailErrorMsg.hide();
		}
	});
	
	//이름 유효성 체크
	var nameErrorMsg = $('#nameErrorMsg');
	var namePattern = /^[가-힣]{2,6}$/;
	
	nameErrorMsg.hide();
	
	$('#name').on('input', function(){
		var nameVal = $.trim($('#name').val());
		if(!namePattern.test(nameVal)){
			nameErrorMsg.show();
			if($('#name').val()==''){
				nameErrorMsg.hide();
			}
		}else{
			nameErrorMsg.hide();
		}
	});
	
	//생년월일 유효성 체크
	var birthErrorMsg = $('#birthErrorMsg');
	var birthPattern = /^[0-9]{6}$/;
	
	birthErrorMsg.hide();
	
	$('#birth').on('input', function(){
		var birthVal = $.trim($('#birth').val());
		var birthLen = $('#birth').val().length;
		//길이가 6자가 넘어가면 오류 메시지 띄운다
		if(birthLen > 6){
		if(!birthPattern.test(birthVal)){
			birthErrorMsg.show();
			if($('#birth').val()==''){
			birthErrorMsg.hide();
			}
		}else{
			birthErrorMsg.hide();
			}
		}else{
			birthErrorMsg.hide();
		}
	});
	
	//집전화 유효성 체크
	var hometelErrorMsg = $('#hometelErrorMsg');
	var phonePattern = /^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}/;
	var hometel = $('#hometel');
	
	hometelErrorMsg.hide();
	
	hometel.on('input', function(){
		var hometelVal = $.trim(hometel.val());
		if(!phonePattern.test(hometelVal)){
			hometelErrorMsg.show();
			if(hometel.val()==''){
				hometelErrorMsg.hide();
			}
		}else{
			hometelErrorMsg.hide();
		}
	});
	
	//아이디 중복체크 => 버튼에 onclick 적지 말고 사용하기
	$('#idCheckBtn').on('click', function(){
		var idPattern = /^[a-zA-Z]{1}[a-zA-Z0-9]{4,14}$/;
		var id = $('#id');	
		var idVal = $.trim(id.val());	
		if(idVal==''){
			alert("사용할 아이디를 입력해주세요");
			id.focus();
			return;
		}
		if(!idPattern.test(idVal)){			
			alert("특수문자를 제외한 5~15자리의 영문자, 숫자만 입력해 주세요");
			id.focus();
			id.val('');
			return;
		}
		console.log(idVal);
		$.ajax({
			url: "checkID.do",
			type: "POST",
			data: {id:$('#id').val()},
			datatype: "json"
		}).done(function(data){
				var json = (new Function('return'+data))();
				if(json[0].param == 'n'){
					if(!confirm("사용 가능한 아이디입니다. 사용하시겠습니까?")){
						id.val('');
						id.focus();
						return;
						}else{
							$('#idCheckBtn').attr('disabled', true);
							id.attr('disabled', true);
						}
					}else{
						alert("중복된 아이디입니다. 다시 시도해 주세요");
						id.val('');
						id.focus();
						return;
					}
			}).fail(function(){
				alert("ajax 오류");
		});	
	});	
	
	//이메일 확인
	function verifyEmail() {
		alert("사용가능한 이메일입니다");
		return;
	}
	
	//휴대전화 본인인증
	function certifyTel() {
		alert("인증이 정상처리되었습니다");
		return;
	}

</script>
</html>