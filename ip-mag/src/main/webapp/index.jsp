<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ja" xml:lang="ja">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Style-Type" content="text/css" />
<meta http-equiv="Content-Script-Type" content="text/JavaScript" />
<link href="${contextPath}/css/home.css" rel="stylesheet"
	type="text/css" media="screen,projection" charset="utf-8" />
<title>IP利用状況</title>
</head>
<body>
	<table border="0" id="tb">
		<tr id="frame">
		<td>接続状況</td>
		<td>IPアドレス</td>
		<td>利用者名</td>
		<td>マシン名</td>
		<td>位置</td>
		<td>備考</td>
		</tr>
		${sessionScope.UD.displayIPAddressAssignment()}
	</table>
</body>
</html>
