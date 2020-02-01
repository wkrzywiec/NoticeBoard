INSERT INTO public.author (creation_date,first_name,last_name) VALUES
('2020-01-26 10:02:50.000','John','Doe')
;

INSERT INTO public.board (creation_date,title) VALUES
('2020-01-26 09:59:35.000','Board 1 title')
,('2020-01-26 10:00:06.000','Board 2 title')
,('2020-01-26 10:02:16.000','Board 3 title')
,('2020-01-26 10:02:56.000','Board 4 title')
,('2020-01-26 10:03:28.000','Board 5 title')
;

INSERT INTO public.notice (creation_date,description,title,board_id,author_id) VALUES
('2020-01-26 10:00:30.000','Notice 1 description','Notice 1 title',1,1)
,('2020-01-26 10:01:19.000','Notice 2 description','Notice 2 title',1,1)
,('2020-01-26 10:01:44.000','Notice 3 description','Notice 3 title',1,1)
,('2020-01-26 10:02:15.000','Notice 4 description','Notice 4 title',2,1)
;