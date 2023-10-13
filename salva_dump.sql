--
-- PostgreSQL database dump
--

-- Dumped from database version 14.9 (Ubuntu 14.9-0ubuntu0.22.04.1)
-- Dumped by pg_dump version 14.9 (Ubuntu 14.9-0ubuntu0.22.04.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: product; Type: TABLE; Schema: public; Owner: angel
--

CREATE TABLE public.product (
    id uuid NOT NULL,
    color character varying(255),
    container_type character varying(255),
    count integer,
    is_fragile boolean,
    lote character varying(255),
    price double precision,
    size integer,
    section_id uuid NOT NULL,
    CONSTRAINT product_container_type_check CHECK (((container_type)::text = ANY ((ARRAY['Cardboard'::character varying, 'Plastic'::character varying, 'Glass'::character varying, 'Nylon'::character varying])::text[])))
);


ALTER TABLE public.product OWNER TO angel;

--
-- Name: section; Type: TABLE; Schema: public; Owner: angel
--

CREATE TABLE public.section (
    id uuid NOT NULL,
    product_type character varying(255),
    size integer,
    CONSTRAINT section_product_type_check CHECK (((product_type)::text = ANY ((ARRAY['ElectricalAppliance'::character varying, 'MeatProduct'::character varying, 'Clothing'::character varying, 'GroomingProduct'::character varying])::text[])))
);


ALTER TABLE public.section OWNER TO angel;

--
-- Name: user_seq; Type: SEQUENCE; Schema: public; Owner: angel
--

CREATE SEQUENCE public.user_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_seq OWNER TO angel;

--
-- Name: users; Type: TABLE; Schema: public; Owner: angel
--

CREATE TABLE public.users (
    id uuid NOT NULL,
    fullname character varying(255),
    password character varying(255),
    phone_number character varying(255),
    username character varying(255),
    roles character varying(255)[]
);


ALTER TABLE public.users OWNER TO angel;

--
-- Data for Name: product; Type: TABLE DATA; Schema: public; Owner: angel
--

COPY public.product (id, color, container_type, count, is_fragile, lote, price, size, section_id) FROM stdin;
\.


--
-- Data for Name: section; Type: TABLE DATA; Schema: public; Owner: angel
--

COPY public.section (id, product_type, size) FROM stdin;
d91bc24e-af16-43c1-b90b-575e0d3e70a7	ElectricalAppliance	100
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: angel
--

COPY public.users (id, fullname, password, phone_number, username, roles) FROM stdin;
b33135c7-b646-4e8d-aadf-e74b3a5182be	Angel Admin	$2a$12$yEOgOyeWL.0/eSqRGfhLeuSMH2ytfoWmdG8XdMv6wLketWGIQ3KDO	+5366666666	admin	{ROLE_ADMIN}
ebd712cd-f3de-46d3-99f7-19441ab2a6c9	Angel User	$2a$12$0w7wp0.qvv7LAu/fGseGNuQc6WS.ecC52FiJZyg7HJpwIyQJnEPBC	+5355555555	angel	{ROLE_USER}
\.


--
-- Name: user_seq; Type: SEQUENCE SET; Schema: public; Owner: angel
--

SELECT pg_catalog.setval('public.user_seq', 1, false);


--
-- Name: product product_pkey; Type: CONSTRAINT; Schema: public; Owner: angel
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_pkey PRIMARY KEY (id);


--
-- Name: section section_pkey; Type: CONSTRAINT; Schema: public; Owner: angel
--

ALTER TABLE ONLY public.section
    ADD CONSTRAINT section_pkey PRIMARY KEY (id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: angel
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: product fksgh2oijrlk36dnrplq8vq7dp8; Type: FK CONSTRAINT; Schema: public; Owner: angel
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT fksgh2oijrlk36dnrplq8vq7dp8 FOREIGN KEY (section_id) REFERENCES public.section(id);


--
-- PostgreSQL database dump complete
--

