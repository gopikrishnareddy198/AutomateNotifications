<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<table>

 <tr>
    <th bgcolor="DodgerBlue">Serial No.</th>
     <th bgcolor="DodgerBlue">Name</th>
     <th bgcolor="DodgerBlue">Account Number</th>
     <th bgcolor="DodgerBlue">Account Balance</th>
     <th bgcolor="DodgerBlue">Email id</th>
     <th bgcolor="DodgerBlue">Email sent status</th>
   </tr>

  <c:forEach var="item" items="${mailsSentList}">
    <tr>
        <td bgcolor="#8EF4A3">${item.serialNumber}</td>
        <td bgcolor="#8EF4A3">${item.accountHolderName}</td>
        <td bgcolor="#8EF4A3">${item.accountNumber}</td>
        <td bgcolor="#8EF4A3">${item.accountBalance}</td>
        <td bgcolor="#8EF4A3">${item.emailId}</td>
        <c:if test = "${item.mailSentStatus}">
            <td bgcolor="LightGray"><span style="color:#00BA26">&#9989;</span></td>
        </c:if>
    </tr>
  </c:forEach>

</table>
