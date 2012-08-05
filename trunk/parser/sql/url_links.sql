-- Create table
create table URL_LINKS
(
  url_id          NUMBER not null,
  link            VARCHAR2(1000),
  last_check DATE
);
-- Create/Recreate indexes 
create unique index URL_LINKS_IDX1 on URL_LINKS (LINK);
-- Create/Recreate primary, unique and foreign key constraints 
alter table URL_LINKS
  add constraint URL_LINKS_PK primary key (URL_ID)
  using index;
  
create sequence feed_seq;
create table  feed(feed_id number, feed_url varchar2(1000),last_update date);

drop table feed_message;
create table feed_message(feed_id number, message varchar2(1000), link varchar2(1000),pub_date date);



