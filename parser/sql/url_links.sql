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
  
  create sequence URL_LINKS_SEQ;
