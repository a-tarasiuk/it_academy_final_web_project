create table accounts
(
    account_id                bigint unsigned auto_increment,
    account_first_name        varchar(50)                                    not null,
    account_last_name         varchar(50)                                    not null,
    account_login             varchar(50)                                    not null,
    account_email             varchar(70)                                    not null,
    account_registration_date date                                           not null,
    account_password_encoded  varchar(255)                                   not null,
    account_role              enum ('ADMINISTRATOR', 'MANAGER', 'FORWARDER') not null,
    account_status            enum ('ACTIVATED', 'NOT_ACTIVATED', 'BANNED')  not null,
    constraint account_email
        unique (account_email),
    constraint account_id
        unique (account_id),
    constraint account_login
        unique (account_login)
);

alter table accounts
    add primary key (account_id);

create table companies
(
    company_id           bigint unsigned auto_increment,
    company_name         varchar(150) not null,
    company_address      varchar(200) not null,
    company_phone_number varchar(16)  not null,
    constraint company_id
        unique (company_id),
    constraint company_name
        unique (company_name)
);

alter table companies
    add primary key (company_id);

create table employees
(
    employee_id bigint unsigned auto_increment,
    account_id  bigint unsigned not null,
    company_id  bigint unsigned not null,
    constraint employee_id
        unique (employee_id),
    constraint employees_ibfk_1
        foreign key (account_id) references accounts (account_id),
    constraint employees_ibfk_2
        foreign key (company_id) references companies (company_id)
);

create index account_id
    on employees (account_id);

create index company_id
    on employees (company_id);

alter table employees
    add primary key (employee_id);

create table offers
(
    offer_id             bigint unsigned auto_increment,
    employee_id          bigint unsigned         not null,
    offer_product_name   varchar(200)            not null,
    offer_product_weight decimal(10, 2)          not null,
    offer_product_volume decimal(10, 2)          not null,
    offer_address_from   varchar(200)            not null,
    offer_address_to     varchar(200)            not null,
    offer_freight        decimal(20, 2)          not null,
    offer_creation_date  date                    not null,
    offer_status         enum ('OPEN', 'CLOSED') not null,
    constraint offer_id
        unique (offer_id),
    constraint offers_ibfk_1
        foreign key (employee_id) references employees (employee_id)
);

create index employee_id
    on offers (employee_id);

alter table offers
    add primary key (offer_id);

create table tokens
(
    token_id     bigint unsigned auto_increment,
    account_id   bigint unsigned                   not null,
    token_number varchar(255)                      not null,
    token_status enum ('CONFIRMED', 'UNCONFIRMED') null,
    constraint account_id
        unique (account_id),
    constraint token_id
        unique (token_id),
    constraint tokens_ibfk_1
        foreign key (account_id) references accounts (account_id)
);

alter table tokens
    add primary key (token_id);

create table tradings
(
    trading_id      bigint unsigned auto_increment,
    offer_id        bigint unsigned                   not null,
    employee_id     bigint unsigned                   not null,
    trading_freight decimal(8, 2)                     not null,
    trading_status  enum ('ACCEPTED', 'NOT_ACCEPTED') not null,
    constraint offer_id
        unique (offer_id, employee_id),
    constraint trading_id
        unique (trading_id),
    constraint tradings_ibfk_1
        foreign key (offer_id) references offers (offer_id),
    constraint tradings_ibfk_2
        foreign key (employee_id) references employees (employee_id)
);

create index employee_id
    on tradings (employee_id);

alter table tradings
    add primary key (trading_id);

create
    definer = root@localhost procedure create_account(IN first_name varchar(50), IN last_name varchar(50),
                                                      IN login varchar(50), IN email varchar(70),
                                                      IN registration_date date, IN password_encoded varchar(255),
                                                      IN role enum ('MODERATOR', 'MANAGER', 'FORWARDER'),
                                                      IN status enum ('NOT_ACTIVATED')) deterministic modifies sql data
    GIN
INSERT accounts(account_first_name, account_last_name, account_login, account_email, account_registration_date,
                account_password_coded, account_role, account_status)
VALUES (first_name, last_name, login, email, registration_date, password_coded, role, status);
end;

create
    definer = root@localhost procedure create_company(IN name varchar(150), IN address varchar(200),
                                                      IN phone_number varchar(16)) deterministic modifies sql data
BEGIN
    INSERT companies(company_name, company_address, company_phone_number)
    VALUES (name, address, phone_number);
END;

create
    definer = root@localhost procedure create_employee(IN account_id bigint unsigned, IN company_id bigint unsigned) deterministic
    modifies sql data
BEGIN
    INSERT employees(account_id, company_id)
    VALUES (account_id, company_id);
END;

create
    definer = root@localhost procedure create_offer(IN employee_id bigint unsigned, IN product_name varchar(200),
                                                    IN product_weight decimal(10, 2), IN product_volume decimal(10, 2),
                                                    IN address_from varchar(200), IN address_to varchar(200),
                                                    IN freight decimal(20, 2), IN creation_date date,
                                                    IN status enum ('OPEN')) deterministic modifies sql data
BEGIN
    INSERT offers(employee_id, offer_product_name, offer_product_weight, offer_product_volume, offer_address_from,
                  offer_address_to, offer_freight, offer_creation_date, offer_status)
    VALUES (employee_id, product_name, product_weight, product_volume, address_from, address_to, freight, creation_date,
            status);
END;

create
    definer = root@localhost procedure create_token(IN account_id bigint unsigned, IN number varchar(255),
                                                    IN status enum ('CONFIRMED', 'UNCONFIRMED')) deterministic
    modifies sql data
BEGIN
    INSERT tokens(account_id, token_number, token_status)
    VALUES (account_id, number, status);
END;

create
    definer = root@localhost procedure create_trading(IN trading_offer_id bigint unsigned,
                                                      IN trading_employee_id bigint unsigned, IN freight decimal(20, 2),
                                                      IN status enum ('NOT_ACCEPTED')) deterministic modifies sql data
BEGIN
    INSERT tradings(offer_id, employee_id, trading_freight, trading_status)
    VALUES (trading_offer_id, trading_employee_id, freight, status);
END;

create
    definer = root@localhost procedure find_account_by_email(IN email varchar(50))
BEGIN
    SELECT account_id,
           account_first_name,
           account_last_name,
           account_login,
           account_email,
           account_registration_date,
           account_password_encoded,
           account_role,
           account_status
    FROM accounts
    WHERE account_email = email;
end;

create
    definer = root@localhost procedure find_account_by_id(IN id bigint unsigned)
BEGIN
    SELECT account_id,
           account_first_name,
           account_last_name,
           account_login,
           account_email,
           account_registration_date,
           account_password_encoded,
           account_role,
           account_status
    FROM accounts
    WHERE account_id = id;
END;

create
    definer = root@localhost procedure find_account_by_login(IN login varchar(50))
BEGIN
    SELECT account_id,
           account_first_name,
           account_last_name,
           account_login,
           account_email,
           account_registration_date,
           account_password_encoded,
           account_role,
           account_status
    FROM accounts
    WHERE account_login = login;
end;

create
    definer = root@localhost procedure find_all_accounts()
BEGIN
    SELECT account_id,
           account_first_name,
           account_last_name,
           account_login,
           account_email,
           account_registration_date,
           account_password_encoded,
           account_role,
           account_status
    FROM accounts;
END;

create
    definer = root@localhost procedure find_all_offers()
BEGIN
    SELECT offer_id,
           employee_id,
           offer_product_name,
           offer_product_weight,
           offer_product_volume,
           offer_address_from,
           offer_address_to,
           offer_freight,
           offer_creation_date,
           offer_status
    FROM offers
    ORDER BY offer_creation_date DESC;
END;

create
    definer = root@localhost procedure find_all_offers_by_employee_id(IN id bigint unsigned)
BEGIN
    SELECT offer_id,
           employee_id,
           offer_product_name,
           offer_product_weight,
           offer_product_volume,
           offer_address_from,
           offer_address_to,
           offer_freight,
           offer_creation_date,
           offer_status
    FROM offers
    WHERE employee_id = id
    ORDER BY offer_creation_date DESC;
END;

create
    definer = root@localhost procedure find_company_by_employee_id(IN id bigint unsigned)
BEGIN
    SELECT c.company_id, c.company_name, c.company_address, c.company_phone_number
    FROM companies c
             INNER JOIN employees e ON e.company_id = c.company_id
    WHERE e.employee_id = id;
END;

create
    definer = root@localhost procedure find_company_by_id(IN id bigint unsigned)
BEGIN
    SELECT company_id, company_name, company_address, company_phone_number
    FROM companies
    WHERE company_id = id;
END;

create
    definer = root@localhost procedure find_company_by_name(IN name varchar(150))
BEGIN
    SELECT company_id, company_name, company_address, company_phone_number
    FROM companies
    WHERE company_name = name;
END;

create
    definer = root@localhost procedure find_employee_by_account_id(IN id bigint unsigned)
BEGIN
    SELECT employee_id, account_id, company_id
    FROM employees
    WHERE account_id = id;
END;

create
    definer = root@localhost procedure find_employee_by_id(IN id bigint unsigned)
BEGIN
    SELECT employee_id, account_id, company_id
    FROM employees
    WHERE employee_id = id;
END;

create
    definer = root@localhost procedure find_employee_list_by_company_id(IN id bigint unsigned)
BEGIN
    SELECT employee_id, account_id, company_id
    FROM employees
    WHERE company_id = id;
END;

create
    definer = root@localhost procedure find_offer_by_id(IN id bigint unsigned)
BEGIN
    SELECT offer_id,
           employee_id,
           offer_product_name,
           offer_product_weight,
           offer_product_volume,
           offer_address_from,
           offer_address_to,
           offer_freight,
           offer_creation_date,
           offer_status
    FROM offers
    WHERE offer_id = id;
END;

create
    definer = root@localhost procedure find_open_offers()
BEGIN
    SELECT offer_id,
           employee_id,
           offer_product_name,
           offer_product_weight,
           offer_product_volume,
           offer_address_from,
           offer_address_to,
           offer_freight,
           offer_creation_date,
           offer_status
    FROM offers
    WHERE offer_status = 'OPEN'
    ORDER BY offer_creation_date DESC;
END;

create
    definer = root@localhost procedure find_password_by_account_id(IN id bigint unsigned)
BEGIN
    SELECT account_password_encoded
    FROM accounts
    WHERE account_id = id;
END;

create
    definer = root@localhost procedure find_password_by_login(IN login varchar(50))
BEGIN
    SELECT account_password_encoded
    FROM accounts
    WHERE account_login = login;
END;

create
    definer = root@localhost procedure find_token_by_account_id(IN id bigint unsigned)
BEGIN
    SELECT token_id, token_number, token_status
    FROM tokens
    WHERE account_id = id;
END;

create
    definer = root@localhost procedure find_trading_by_id(IN id bigint unsigned)
BEGIN
    SELECT trading_id, offer_id, employee_id, trading_freight, trading_status
    FROM tradings
    WHERE trading_id = id;
END;

create
    definer = root@localhost procedure find_trading_by_offer_id_and_employee_id(IN offer_id bigint unsigned, IN employee_id bigint unsigned)
BEGIN
    SELECT t.trading_id, t.offer_id, t.employee_id, t.trading_freight, t.trading_status
    FROM tradings t
    WHERE t.offer_id = offer_id
      AND t.employee_id = employee_id;
END;

create
    definer = root@localhost procedure find_tradings_by_employee_id(IN id bigint unsigned)
BEGIN
    SELECT trading_id, offer_id, employee_id, trading_freight, trading_status
    FROM tradings
    WHERE employee_id = id
    ORDER BY trading_freight;
END;

create
    definer = root@localhost procedure find_tradings_by_offer_id(IN id bigint unsigned)
BEGIN
    SELECT trading_id, offer_id, employee_id, trading_freight, trading_status
    FROM tradings
    WHERE offer_id = id
    ORDER BY trading_freight;
END;

create
    definer = root@localhost procedure set_password_by_account_id(IN id bigint unsigned, IN password_encoded varchar(255)) deterministic
    modifies sql data
BEGIN
    UPDATE accounts
    SET account_password_encoded = password_encoded
    WHERE account_id = id;
END;

create
    definer = root@localhost procedure update_account(IN id bigint unsigned, IN first_name varchar(50),
                                                      IN last_name varchar(50), IN login varchar(50),
                                                      IN email varchar(70), IN registration_date date,
                                                      IN role enum ('ADMINISTRATOR', 'MODERATOR', 'MANAGER', 'FORWARDER'),
                                                      IN status enum ('ACTIVATED', 'NOT_ACTIVATED', 'BANNED')) deterministic
    modifies sql data
BEGIN
    UPDATE accounts
    SET account_first_name        = first_name,
        account_last_name         = last_name,
        account_login             = login,
        account_email             = email,
        account_registration_date = registration_date,
        account_role              = role,
        account_status            = status
    WHERE account_id = id;
END;

create
    definer = root@localhost procedure update_account_status_by_id(IN id bigint unsigned, IN status enum ('ACTIVATED', 'BANNED')) deterministic
    modifies sql data
BEGIN
    UPDATE accounts
    SET account_status = status
    WHERE account_id = id;
END;

create
    definer = root@localhost procedure update_company_by_id(IN id bigint unsigned, IN name varchar(150),
                                                            IN address varchar(200), IN phone_number varchar(16))
BEGIN
    UPDATE companies
    SET company_name         = name,
        company_address      = address,
        company_phone_number = phone_number
    WHERE company_id = id;
END;

create
    definer = root@localhost procedure update_offer_by_id(IN id bigint unsigned, IN product_name varchar(200),
                                                          IN product_weight decimal(10, 2),
                                                          IN product_volume decimal(10, 2),
                                                          IN address_from varchar(200), IN address_to varchar(200),
                                                          IN freight decimal(20, 2)) deterministic modifies sql data
BEGIN
    UPDATE offers
    SET offer_product_name   = product_name,
        offer_product_weight = product_weight,
        offer_product_volume = product_volume,
        offer_address_from   = address_from,
        offer_address_to     = address_to,
        offer_freight        = freight
    WHERE offer_id = id;
END;

create
    definer = root@localhost procedure update_offer_status_by_id(IN id bigint unsigned, IN status enum ('OPEN', 'CLOSED')) deterministic
    modifies sql data
BEGIN
    UPDATE offers SET offer_status = status WHERE offer_id = id;
END;

create
    definer = root@localhost procedure update_token(IN id bigint unsigned, IN id_account bigint unsigned,
                                                    IN number varchar(255),
                                                    IN status enum ('CONFIRMED', 'UNCONFIRMED')) deterministic
    modifies sql data
BEGIN
    UPDATE tokens
    SET account_id   = id_account,
        token_status = status,
        token_number = number
    WHERE token_id = id;
END;

create
    definer = root@localhost procedure update_trading_status_by_id(IN id bigint unsigned, IN status enum ('ACCEPTED', 'NOT_ACCEPTED')) deterministic
    modifies sql data
BEGIN
    UPDATE tradings
    SET trading_status = status
    WHERE trading_id = id;
END;


