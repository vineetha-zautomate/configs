CREATE SCHEMA hms;

CREATE TABLE hms.UI_configurations (
    id SERIAL PRIMARY KEY,
    domain_name VARCHAR(255) NOT NULL,
    configuration_json JSONB NOT NULL
);

-- Insert 1: Default light theme configuration for local domain
INSERT INTO hms.UI_configurations (domain_name, configuration_json)
VALUES (
    'local',
    '{
        "theme": {
            "name": "light",
            "background_color": "#ffffff",
            "text_color": "#000000",
            "primary_button_color": "#007bff",
            "secondary_button_color": "#6c757d"
        },
        "layout": {
            "header": true,
            "footer": true,
            "sidebar": false
        },
        "fonts": {
            "header_font": "Arial",
            "body_font": "Helvetica"
        }
    }'
);

select * from hms.ui_configurations;

CREATE TABLE hms.countries (
	country_id serial4 NOT NULL,
	country_name varchar(100) NULL,
	country_code varchar(10) NULL,
	CONSTRAINT countries_pkey PRIMARY KEY (country_id)
);

CREATE TABLE hms.states (
	state_id serial4 NOT NULL,
	state_name varchar(100) NULL,
	state_code varchar(10) NULL,
	country_id int4 NULL,
	CONSTRAINT states_pkey PRIMARY KEY (state_id),
	CONSTRAINT states_country_id_fkey FOREIGN KEY (country_id) REFERENCES public.countries(country_id)
);

CREATE TABLE hms.city (
	city_id serial4 NOT NULL,
	city_name varchar(100) NULL,
	state_id int4 NULL,
	country_id int4 NULL,
	CONSTRAINT city_pkey PRIMARY KEY (city_id),
	CONSTRAINT city_country_id_fkey FOREIGN KEY (country_id) REFERENCES public.countries(country_id),
	CONSTRAINT city_state_id_fkey FOREIGN KEY (state_id) REFERENCES public.states(state_id)
);


INSERT INTO hms.countries
(country_id, country_name, country_code)
VALUES(1, 'India', 'IN');
INSERT INTO hms.countries
(country_id, country_name, country_code)
VALUES(2, 'United States', 'US');

INSERT INTO hms.states
(state_id, state_name, state_code, country_id)
VALUES(1, 'Karnataka', 'KA', 1);
INSERT INTO hms.states
(state_id, state_name, state_code, country_id)
VALUES(2, 'California', 'CA', 2);

INSERT INTO hms.city
(city_id, city_name, state_id, country_id)
VALUES(3, 'Bangalore', 1, 1);

INSERT INTO hms.city
(city_id, city_name, state_id, country_id)
VALUES(4, 'Los Angeles', 2, 2);


