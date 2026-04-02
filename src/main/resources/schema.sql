create table achievements (
                              achievement_id  uuid primary key,
                              title           varchar(100) not null,
                              description     text,
                              badge           varchar(255),
                              xp_required     integer not null default 0
);

create table app_users (
                           app_user_id     uuid primary key,
                           username        varchar(50) not null unique,
                           email           varchar(255) not null unique,
                           password        varchar(255) not null,
                           level           integer not null default 1,
                           xp              integer not null default 0,
                           profile_image   varchar(255),
                           is_verified     boolean not null default false,
                           created_at      timestamp not null default current_timestamp
);

-- Separate OTP table
create table otp_verifications (
                                   otp_id      uuid primary key,
                                   app_user_id uuid not null references app_users on delete cascade,
                                   otp_code    varchar(10) not null,
                                   expires_at  timestamp not null,
                                   is_used     boolean not null default false,
                                   created_at  timestamp not null default current_timestamp
);

create type frequency_type as enum ('daily', 'weekly', 'monthly');

create table habits (
                        habit_id        uuid primary key,
                        title           varchar(100) not null,
                        description     text,
                        frequency       frequency_type not null,
                        is_active       boolean not null default true,
                        app_user_id     uuid not null references app_users on delete cascade,
                        created_at      timestamp not null default current_timestamp
);

create table habit_logs (
                            habit_log_id    uuid primary key,
                            status          varchar(50) not null,
                            xp_earned       integer not null default 0,
                            habit_id        uuid not null references habits on delete cascade,
                            created_at      timestamp not null default current_timestamp
);

create table app_user_achievements (
                                       app_user_achievement_id uuid primary key,
                                       app_user_id             uuid not null references app_users on delete cascade,
                                       achievement_id          uuid not null references achievements on delete cascade,
                                       achieved_at             timestamp not null default current_timestamp,
                                       unique(app_user_id, achievement_id)  -- prevent duplicate awards
);