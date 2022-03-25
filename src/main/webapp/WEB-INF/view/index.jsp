<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<style>
.button {
  background-color: #4CAF50;
  border: none;
  color: white;
  padding: 15px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
  cursor: pointer;
}
</style>
</head>

<form method="get" action="/send-emails">
    <button class="button" type="submit">Send Emails to overdrafted accounts</button>
</form>

   <table>

 <tr>
    <th bgcolor="DodgerBlue">Serial No.</th>
     <th bgcolor="DodgerBlue">Name</th>
     <th bgcolor="DodgerBlue">Adress</th>
     <th bgcolor="DodgerBlue">City</th>
     <th bgcolor="DodgerBlue">Zipcode</th>
     <th bgcolor="DodgerBlue">County</th>
     <th bgcolor="DodgerBlue">Account Number</th>
     <th bgcolor="DodgerBlue">Account Balance</th>
     <th bgcolor="DodgerBlue">Email id</th>
   </tr>

   <c:forEach items="${accountsList}" var="account">
        <tr>
             <td bgcolor="LightGray"> ${account.serialNumber}</td>
             <td bgcolor="LightGray"> ${account.accountHolderName}</td>
             <td bgcolor="LightGray"> ${account.addressLine1}</td>
             <td bgcolor="LightGray"> ${account.city}</td>
             <td bgcolor="LightGray">${account.zipcode}</td>
             <td bgcolor="LightGray">${account.county}</td>
             <td bgcolor="LightGray">${account.accountNumber}</td>
             <c:if test = "${account.accountBalance >= 0}">
                <td bgcolor="MediumSeaGreen">${account.accountBalance}</td>
             </c:if>
            <c:if test = "${account.accountBalance < 0}">
                <td bgcolor="Tomato">${account.accountBalance}</td>
            </c:if>
             <td bgcolor="LightGray"> ${account.emailId}</td>
       </tr>
    </c:forEach>

     </table>

</html>