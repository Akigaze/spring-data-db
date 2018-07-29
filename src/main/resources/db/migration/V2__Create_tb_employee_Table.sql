CREATE TABLE tb_employee (
  id int AUTO_INCREMENT PRIMARY KEY,
  company_id int,
  name VARCHAR(20) NOT NULL,
  gender varchar(10) not null default 'male',
  created_date TIMESTAMP DEFAULT NOW()
);
