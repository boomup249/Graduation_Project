<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
<script type = "text/javascript">
function check(id){	
	if (id == ""){
		alert("id를 입력하세요");
		return;
	}
	url = "check.jsp?id="+id;
	window.open(url,'_blank', 'toolbar=no,status=no,menubar=no,scrollbars=no,resizable=no,left=10000, top=10000, width=10, height=10, visible=none', '');
}
var maxcnt = 3;	
var cnt = 0; 

function CountChecked(field){ 	
	if (field.checked) {			
		count += 1;					
	}
	else {							
		count -= 1;				
	}
	
	if (count > maxCount) {				
		alert("최대 3개까지만 선택가능합니다!");	
	field.checked = false;					
	count -= 1;								
	}
}
</script>
<form action="signup.jsp" method="post">
<table align=center>
<tr><td colspan=2 align=center height=40><b>회원가입</b><td></tr>
<tr>
    <td align=right>아이디&nbsp;</td>
    <td><input type="text" name="id" id="id" placeholder="ID 입력" required></td>
    <td><input type = "button" value = "중복 확인" onclick = "check(this.form.id.value)"></td>
</tr>
<tr>
    <td align=right>패스워드&nbsp;</td>
    <td><input type="password" name="ps" required></td>
</tr>
<tr> 
    <td align=right>패스워드(확인)&nbsp;</td>
    <td><input type="password" name="ps2" required></td>
</tr>
<tr>
    <td align=right>생일&nbsp;</td>
    <td><input type="date" name="birth" min="1900-01-01" max="2030-12-31" required></td> 
</tr>
<tr>
    <td align=right>성별&nbsp;</td>
    <td><input type="radio" name="gender" value="m" required>남</td>
    <td><input type="radio" name="gender" value="f" required>여</td> 
</tr>
<tr>
    <td align=right>선호장르&nbsp;</td>
    <td><input onclick="CountChecked(this)" type="checkbox" name="genre" value="action" >액션</td>
    <td><input onclick="CountChecked(this)" type="checkbox" name="genre" value="a_adventure">액션어드벤처</td> 
    <td><input onclick="CountChecked(this)" type="checkbox" name="genre" value="survival">생존</td> 
    <td><input onclick="CountChecked(this)" type="checkbox" name="genre" value="shooting" >슈팅</td> 
    <td><input onclick="CountChecked(this)" type="checkbox" name="genre" value="fps" >FPS</td> 
    <td><input onclick="CountChecked(this)" type="checkbox" name="genre" value="rpg" >RPG</td> 
    <td><input onclick="CountChecked(this)" type="checkbox" name="genre" value="arpg" >ARPG</td> 
    <td><input onclick="CountChecked(this)" type="checkbox" name="genre" value="mmo" >MMORPG</td> 
    <td><input onclick="CountChecked(this)" type="checkbox" name="genre" value="openworld" >오픈월드</td> 
    <td><input onclick="CountChecked(this)" type="checkbox" name="genre" value="hns" >핵 앤 슬래시</td> 
    <td><input onclick="CountChecked(this)" type="checkbox" name="genre" value="adventure" >어드벤처</td> 
    <td><input onclick="CountChecked(this)" type="checkbox" name="genre" value="sports" >스포츠</td> 
    <td><input onclick="CountChecked(this)" type="checkbox" name="genre" value="racing" >레이싱</td> 
    <td><input onclick="CountChecked(this)" type="checkbox" name="genre" value="casual" >캐주얼</td> 
    <td><input onclick="CountChecked(this)" type="checkbox" name="genre" value="puzzle" >퍼즐</td>  
</tr>

<tr>
    <td align=right>비선호장르&nbsp;</td>
    <td><input onclick="CountChecked(this)" type="checkbox" name="dgenre" value="action" >액션</td>
    <td><input onclick="CountChecked(this)" type="checkbox" name="dgenre" value="a_adventure" >액션어드벤처</td> 
    <td><input onclick="CountChecked(this)" type="checkbox" name="dgenre" value="survival" >생존</td> 
    <td><input onclick="CountChecked(this)" type="checkbox" name="dgenre" value="shooting" >슈팅</td> 
    <td><input onclick="CountChecked(this)" type="checkbox" name="dgenre" value="fps" >FPS</td> 
    <td><input onclick="CountChecked(this)" type="checkbox" name="dgenre" value="rpg" >RPG</td> 
    <td><input onclick="CountChecked(this)" type="checkbox" name="dgenre" value="arpg" >ARPG</td> 
    <td><input onclick="CountChecked(this)" type="checkbox" name="dgenre" value="mmo" >MMORPG</td> 
    <td><input onclick="CountChecked(this)" type="checkbox" name="dgenre" value="openworld" >오픈월드</td> 
    <td><input onclick="CountChecked(this)" type="checkbox" name="dgenre" value="hns" >핵 앤 슬래시</td> 
    <td><input onclick="CountChecked(this)" type="checkbox" name="dgenre" value="adventure" >어드벤처</td> 
    <td><input onclick="CountChecked(this)" type="checkbox" name="dgenre" value="sports" >스포츠</td> 
    <td><input onclick="CountChecked(this)" type="checkbox" name="dgenre" value="racing" >레이싱</td> 
    <td><input onclick="CountChecked(this)" type="checkbox" name="dgenre" value="casual" >캐주얼</td> 
    <td><input onclick="CountChecked(this)" type="checkbox" name="dgenre" value="puzzle" >퍼즐</td>  
</tr>
<tr>
    <td colspan=2 align=center height=50>
        <input type="submit" value="회원가입하기">
    </td>
</tr>
</table>
</form>
</body>
</html>