<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ja" xml:lang="ja">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Script-Type" content="text/JavaScript" />
<title>登録情報削除確認</title>
</head>

<body>
	<table border="0">
		<tr>
			<td align="right"><b>IPアドレス:</b></td>
			<td>${f:out(action.ip)}</td>
		</tr>
		<tr>
			<td align="right"><b>利用者名:</b></td>
			<td>${f:out(action.name)}</td>
		</tr>
		<tr>
			<td align="right"><b>マシン名:</b></td>
			<td>${f:out(action.machine)}</td>
		</tr>
		<tr>
			<td align="right"><b>位置:</b></td>
			<td>${f:out(action.position)}</td>
		</tr>
		<tr>
			<td align="right"><b>備考:</b></td>
			<td>${f:out(action.etc)}</td>
		</tr>
	</table>	
	
	<p></p>
	を登録解除します。
	<t:form action="complete" value="${action}">
	<input type="submit" value="解除" />
	</t:form>	
</body>

</html>