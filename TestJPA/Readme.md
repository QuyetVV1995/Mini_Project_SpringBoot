Bài tập từ file person.sql viết các phương thức sau:
- groupPeopleByCity(); //Gom tất cả những người trong cùng một thành phố lại
- groupJobByCount();  //Nhóm các nghề nghiệp và đếm số người làm mỗi nghề
- findTop5Jobs(); //Tìm 5 nghề có số lượng người làm nhiều nhất sắp xếp từ cao xuống thấp
- findTop5Citis(); //Tìm 5 thành phố có số người thuộc danh sách sinh sống đông nhất từ vị trí thứ 5 đến vị trí thứ 1
- averageJobSalary(); //Ứng với mỗi nghề nghiệp (job - String), tính mức lương trung bình (float)
- top5HighestSalaryCities(); //Tìm 5 thành phố có mức lương trung bình cao nhất, sắp xếp từ cao xuống thấp
- averageJobAge(); //Ứng với mỗi loại job hãy tính độ tuổi trung bình
- averageCityAge(); //Ứng với mỗi thành phố hãy tính độ tuổi trung bình

Giải thích 1 số câu lệnh SQL sử dụng trong code:
Với một nghề cụ thể, hãy tìm ra 5 thành phố có nhiều làm nghề đó nhất:
Nhóm city, job. Đếm job trong thành phố

`SELECT city, job, count(job) FROM PERSON GROUP BY city, job HAVING job = 'Accountant' ORDER BY 3 DESC LIMIT 5`


Sắp xếp theo mức lương trung bình

`SELECT city, job, count(job), avg(salary) FROM PERSON GROUP BY city, job HAVING job = 'Accountant' ORDER BY 4 DESC LIMIT 5`
