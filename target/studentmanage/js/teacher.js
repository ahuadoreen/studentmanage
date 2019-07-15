var url = "http://localhost:8080/studentmanage/teacher/";
$(document).ready(function() {
	$.get(url + "teachers",function(data,status){
		var teachers = data.teachers;
		for(var item in teachers){
			var template = '<tr><td>' + teachers[item].teacherName + '</td>'
			+ '<td>' + (teachers[item].gender == 0?'男':'女') + '</td>'
					+ '<td>' + teachers[item].teacherAge + '</td>'
							+ '<td>' + teachers[item].teacherSubject + '</td></tr>';
			$("#dataList").append(template);
		}
	  });
});