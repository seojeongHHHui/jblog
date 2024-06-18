select * from user;
select * from blog;
select * from category;
select * from post;

delete from user;

update blog set logo = '/assets/images/spring-logo.jpg';

select * from blog where id='test';

insert
into blog
values(id, title, logo);

insert
into category
values(null, '기본', '기본 카테고리', now(), id);

select a.no, a.name, a.description, a.reg_date as regDate, a.id, count(b.no) as postCount
from category a left join post b
on a.no = b.category_no 
where a.id='test'
group by a.no, a.name, a.description, a.reg_date, a.id
order by no asc;

insert
into post
values(null, "test title", "test contesnts", now(), 1);

select min(no)
from category
where id='test';

select no, title, contents, reg_date as regDate, category_no as categoryNo
from post
where category_no=1
order by no asc;

select min(no)
from post
where category_no=1;

select count(*)
from post
where category_no=1;

insert
into category
values(null, "test", "test", now(), "test");

update post
set category_no = "#{defaultCategoryNo }"
where category_no="#{no }";

