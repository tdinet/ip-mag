<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ja" xml:lang="ja">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Script-Type" content="text/JavaScript" />
<title>利用者情報</title>
</head>
<body>
	<h3>IPアドレス 192.tes.tes</h3>
	<t:form action="register" value="${action}">
	<table border="1">
		<tr>
			<td align="right">利用者名:</td>
			<td><t:input type="text" name="name" size="30" maxlength="20" /></td>
		</tr>
		<tr>
			<td align="right">マシン名:</td>
			<td><t:input type="text" name="machine" size="30" maxlength="20" /></td>
		</tr>
		<tr>
			<td align="right">位置:</td>
			<td><t:input type="text" name="position" size="30" maxlength="20" /></td>
		</tr>
		<tr>
			<td align="right">備考:</td>
			<td><t:input type="text" name="etc" size="30" maxlength="20" /></td>
		</tr>
		<tr>
			<td></td>			
			<td align="right">
				
	        <input type="submit" value="確認"/>
			
			</td>
		</tr>				
	</table>	
	</t:form>
	<span style="color:red">${action.err}</span>	
</body>
</html>