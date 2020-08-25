<%@ page import="com.ukrainer.infostroy.model.User" %><%--
  Created by IntelliJ IDEA.
  User: david
  Date: 8/24/20
  Time: 3:00 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="css/student-table.css">

    <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
    <script src="js/config/jquery.js"></script>
    <link href="https://use.fontawesome.com/releases/v5.0.8/css/all.css" rel="stylesheet">
</head>
<body>

<div class="header_u">
    <div class="log_out">
        <a href="/LogOutServlet">LOG OUT</a>
    </div>
    <div class="user">
        <span><%= ((User) session.getAttribute("user")).getName()%></span>
    </div>
</div>

<div class="content_wrapper">

    <div class="main_container" id="main">
        <div class="content">
            <div class="align_stud_table">
                <div class="align_filters">
                    <div class="filters">
                        <div class="select_pageSize">
                            <label> Show
                                <select class="pageNum" name="pageSize">
                                    <option value="10">10</option>
                                    <option value="15">15</option>
                                    <option value="25">25</option>
                                    <option value="50">50</option>
                                </select>
                            </label>
                        </div>
                        <div class="search_byTable">
                            <input type="text" class="tableSearch" placeholder="Search....">
                        </div>
                    </div>
                </div>


                <table class="student_table" id="tab">
                    <thead>
                    <tr>
                        <th data-param="id">
                            <div class="rows">
                                <a href="#" class="line" data-sort="none" id="id">
                                    <span>ID</span>
                                    <div class="icon_sort">
                                        <i class="fa fa-angle-up muted"></i>
                                        <i class="fa fa-angle-down muted"></i>
                                    </div>
                                </a>
                            </div>
                        </th>
                        <th data-param="id">
                            <div class="rows">
                                <a href="#" class="line" data-sort="none" id="Name">
                                    <span>Name</span>
                                    <div class="icon_sort">
                                        <i class="fa fa-angle-up muted"></i>
                                        <i class="fa fa-angle-down muted"></i>
                                    </div>
                                </a>
                            </div>
                        </th>
                        <th data-param="faculty">
                            <div class="rows">
                                <a href="#" class="line" data-sort="none" id="Surname">
                                    <span>Surname</span>
                                    <div class="icon_sort">
                                        <i class="fa fa-angle-up muted"></i>
                                        <i class="fa fa-angle-down muted"></i>
                                    </div>
                                </a>
                            </div>
                        </th>
                        <th data-param="city">
                            <div class="rows">
                                <a href="#" class="line" data-sort="none" id="City">
                                    <span>City</span>
                                    <div class="icon_sort">
                                        <i class="fa fa-angle-up muted"></i>
                                        <i class="fa fa-angle-down muted"></i>
                                    </div>
                                </a>
                            </div>
                        </th>
                        <th data-param="email">
                            <div class="rows">
                                <a href="#" class="line" data-sort="none" id="Email">
                                    <span>Email</span>
                                    <div class="icon_sort">
                                        <i class="fa fa-angle-up muted"></i>
                                        <i class="fa fa-angle-down muted"></i>
                                    </div>
                                </a>
                            </div>
                        </th>


                        <th data-param="status">
                            <div class="rows">
                                <a href="#" class="line" data-sort="none" id="Status">
                                    <span>Status</span>
                                </a>
                            </div>
                        </th>


                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>

            <div class="pag_align">
                <div class="align_pagination">
                    <div class="prev_btn"><button class="prev">Prev </button></div>
                    <ul class="pagination">

                    </ul>
                    <div class="next_btn"><button class="next">Next</button></div>
                </div>
            </div>

        </div>
    </div>
</div>

<script src="js/websocket.js"></script>
<script src="js/table/table.js"></script>
<script src="js/admin/user_table.js"></script>

</body>
</html>