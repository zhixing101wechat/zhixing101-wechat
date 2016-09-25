<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TestDubbo</title>
</head>
<body>
	<table>
		<tr>
			<td>author</td>
			<td>${book.author}</td>
		</tr>
		<tr>
			<td>summary</td>
			<td>${book.summary}</td>
		</tr>
		<tr>
			<td>title</td>
			<td>${book.title}</td>
		</tr>
		<tr>
			<td>publisher</td>
			<td>${book.publisher}</td>
		</tr>
		<tr>
			<td>binding</td>
			<td>${book.binding}</td>
		</tr>
		<tr>
			<td>subtitle</td>
			<td>${book.version}</td>
		</tr>
		<tr>
			<td>price</td>
			<td>${book.price}</td>
		</tr>
		<tr>
			<td>frontCover</td>
			<td><img src="${book.frontCover}" /></td>
		</tr>
		<tr>
			<td>isbn10</td>
			<td>${book.isbn10}</td>
		</tr>
		<tr>
			<td>isbn13</td>
			<td>${book.isbn13}</td>
		</tr>
	</table>
</body>
</html>