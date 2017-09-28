drop table if exists service_provider;
create table service_provider (
  id varchar(127) primary key,
  name varchar(127),
  secret_key varchar(127),
  description varchar(255),
  home_url varchar(1023),
  access_service_provider_policy int,
  logout_urls varchar(1023),
  needed_attributes varchar(1023),
  created_time datetime,
  modified_time datetime
);