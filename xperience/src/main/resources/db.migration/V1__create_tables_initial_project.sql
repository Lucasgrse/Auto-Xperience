CRETE TABLE IF EXISTS user (
    id uuid NOT NULL DEFAULT uuid_generate_v4(),
    email varchar(255) UNIQUE NOT NULL,
    password varchar(255) NOT NULL,
    physical_person_id uuid REFERENCES physical_person(id),
    legal_person_id uuid REFERENCES legal_person(id),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE,
    deleted_at TIMESTAMP WITH TIME ZONE,
    CONSTRAINT user_pkey PRIMARY KEY (id)
 );

CREATE TABLE IF EXISTS physical_person (
    id uuid NOT NULL DEFAULT uuid_generate_v4(),
    cpf varchar(255) UNIQUE NOT NULL,
    birth_date date,
    photo_profile varchar(255) NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE,
    deleted_at TIMESTAMP WITH TIME ZONE,
    CONSTRAINT natural_person_pkey PRIMARY KEY (id),
    CONSTRAINT natural_person_cpf_key UNIQUE (cpf)
);

CREATE TABLE IF EXISTS legal_person (
    id uuid NOT NULL DEFAULT uuid_generate_v4(),
    cnpj varchar(255) UNIQUE NOT NULL,
    photo_profile varchar(255) NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE,
    deleted_at TIMESTAMP WITH TIME ZONE,
    CONSTRAINT legal_person_pkey PRIMARY KEY (id),
    CONSTRAINT legal_person_cnpj_key UNIQUE (cnpj)
);


CREATE TABLE IF EXISTS comment (
    id uuid NOT NULL DEFAULT uuid_generate_v4(),
    comment_user_id REFERENCES comment (id),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    deleted_at TIMESTAMP WITH TIME ZONE,
);
