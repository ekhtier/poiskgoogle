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

-- Create table
create table FEED
(
  feed_id     NUMBER not null,
  feed_url    VARCHAR2(1000),
  last_update DATE
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table FEED
  add constraint FEED_PK primary key (FEED_ID)
  using index;
--drop table feed_message;
create table FEED_MESSAGE
(
  feed_id     NUMBER not null,
  message     VARCHAR2(1000),
  link        VARCHAR2(1000) not null,
  pub_date    DATE not null,
  insert_date DATE,
  description VARCHAR2(4000)
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table FEED_MESSAGE
  add constraint FEED_MESSAGE_PK primary key (FEED_ID, LINK)
  using index;
alter table FEED_MESSAGE
  add constraint FEED_MESSAGE_FK foreign key (FEED_ID)
  references FEED (FEED_ID);



