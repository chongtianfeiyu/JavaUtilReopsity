<html>
<body>
<h2>添加一个BOOK Add Book</h2>
<form method="post" action="<%=request.getContextPath() %>/book.do?method=add">
bookname:<input type="text" name="name" id="name">
author:<input type="text" name="author" id="author">
<input type="submit" value="Add">
</form>
 
<h2>测试从request中得到请求参数</h2>
<form method="post" action="<%=request.getContextPath() %>/book.do?method=request">
bookname:<input type="text" name="name" id="name">
author:<input type="text" name="author" id="author">
<input type="submit" value="Add">
</form>

<h2>测试Base control</h2>
<form method="post" action="<%=request.getContextPath() %>/testBase?method=test1">
bookname:<input type="text" name="name" id="name">
author:<input type="text" name="author" id="author">
<input type="submit" value="Add">
</form>


</body>
</html>
