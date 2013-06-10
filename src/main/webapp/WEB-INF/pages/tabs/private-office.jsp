<%--
  User: Danil
  Date: 03.06.13
  Time: 20:15
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<div class="container-fluid">
    <div class="row-fluid">
        <div class="span6" style="float: left">
            <div class="span5" style="float: left">
                <label for="surname" class="required_label">Фамилия<span class="asterisk_red">*</span></label> <br>
                <label for="name" class="required_label">Имя<span class="asterisk_red">*</span></label> <br>
                <label for="patronymic" class="required_label">Отчество<span class="asterisk_red">*</span></label>
            </div>
            <div class="span6">
                <input id="surname" type="text" class="search-query" required><br>
                <input id="name" type="text" class="search-query" required> <br>
                <input id="patronymic" type="text" class="search-query" required>
            </div>
        </div>

        <div class="span6">
            <div class="span6" style="float: left">
                <label for="date_of_birth" class="required_label">Дата рождения<span class="asterisk_red">*</span></label> <br>
                <label for="email" class="required_label">Почта<span class="asterisk_red">*</span></label> <br>
                <label for="telephone" class="required_label">Контактный телефон<span class="asterisk_red">*</span></label>
            </div>
            <div class="span6">
                <input id="date_of_birth" type="text" class="search-query" required> <br>
                <input id="email" type="email" class="search-query" required> <br>
                <input id="telephone" type="tel" class="search-query" required pattern="[0-9]{,11}">
            </div>
        </div>
    </div>
</div>


