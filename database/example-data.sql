--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = public, pg_catalog;

--
-- Data for Name: location; Type: TABLE DATA; Schema: public; Owner: atehwa
--

COPY location (id, coord, modtime, mergedto) FROM stdin;
1	(24.9354499999999994,60.1695199999999986)	2015-12-30 12:23:07.57373	\N
2	(25.468160000000001,65.012360000000001)	2015-12-30 12:23:07.57373	\N
3	(31.5,61)	2015-12-30 12:23:07.57373	\N
4	(22.8954300000000011,59.8088499999999996)	2015-12-30 13:20:08.551407	\N
5	(-16.4557991027832031,63.9416999816894531)	2016-01-04 14:52:34.256563	\N
6	(24.9137306213378906,60.1731109619140625)	2016-01-07 15:28:26.890563	\N
7	(24.93072509765625,60.1809883117675781)	2016-01-07 15:38:13.111897	\N
8	(24.9605503082275391,60.1799201965332031)	2016-01-07 15:38:52.452563	\N
9	(24.9748420715332031,60.1759071350097656)	2016-01-07 15:39:39.168762	\N
10	(24.9309825897216797,60.1870880126953125)	2016-01-07 16:14:33.48631	\N
11	(24.9444522857666016,60.1710433959960938)	2016-01-07 16:44:42.15433	\N
12	(24.9694938659667969,60.1740074157714844)	2016-01-07 16:53:43.302995	\N
13	(24.9560337066650391,60.1846237182617188)	2016-01-07 16:59:14.956711	\N
14	(24.9473438262939453,60.1736221313476562)	2016-01-07 17:02:32.763394	\N
15	(24.9860191345214844,60.2135696411132812)	2016-01-07 17:22:25.077005	\N
16	(25.0262317657470703,60.2302398681640625)	2016-01-07 17:34:50.341285	\N
17	(25.0293464660644531,60.2331008911132812)	2016-01-07 17:35:23.910399	\N
18	(25.0281429290771484,60.2318763732910156)	2016-01-07 18:22:18.374527	\N
19	(24.9361248016357422,60.1767463684082031)	2016-01-08 11:30:34.952816	\N
20	(24.9996471405029297,60.207672119140625)	2016-01-11 11:28:34.797082	\N
\.


--
-- Name: location_id_seq; Type: SEQUENCE SET; Schema: public; Owner: atehwa
--

SELECT pg_catalog.setval('location_id_seq', 20, true);


--
-- Data for Name: location_tag; Type: TABLE DATA; Schema: public; Owner: atehwa
--

COPY location_tag (location, tag) FROM stdin;
1	2
1	3
2	2
3	4
4	8
4	9
5	10
5	11
5	12
6	13
7	14
8	15
9	16
9	17
9	18
10	19
11	20
12	21
13	22
13	23
14	24
15	25
15	26
16	27
16	28
17	29
18	30
19	31
20	32
\.


--
-- Data for Name: tag; Type: TABLE DATA; Schema: public; Owner: atehwa
--

COPY tag (id, name, ns, modtime, priority, icon) FROM stdin;
1	deleted	system	2015-12-30 12:23:07.577818	10	\N
2	city	testuser	2015-12-30 12:23:07.577818	50	\N
3	capital	testuser	2015-12-30 12:23:07.577818	60	\N
4	lake	testuser	2015-12-30 12:23:07.577818	50	\N
5	tree	testuser	2015-12-30 13:00:10.19479	50	\N
6	summon	testuser	2015-12-30 13:03:58.089263	50	\N
7	shrine	testuser	2015-12-30 13:05:25.672191	50	\N
8	cliffs	testuser	2015-12-30 13:20:08.564743	50	\N
9	fireplace	testuser	2015-12-30 13:48:25.005383	50	\N
11	ice	anonymous	2016-01-04 15:57:25.997233	50	\N
12	jää	anonymous	2016-01-04 15:57:26.021465	50	\N
13	ei ketään	anonymous	2016-01-07 15:28:26.910881	50	\N
14	capoeira	anonymous	2016-01-07 15:38:13.130302	50	\N
15	puu	anonymous	2016-01-07 15:38:52.469945	50	\N
16	sauna	anonymous	2016-01-07 15:39:39.185042	50	\N
17	ranta	anonymous	2016-01-07 15:39:39.215229	50	\N
18	bileet	anonymous	2016-01-07 15:39:39.239424	50	\N
19	kaakaoautomaatti	anonymous	2016-01-07 16:14:33.503725	50	\N
20	luistelu	anonymous	2016-01-07 16:44:42.169108	50	\N
21	kiipeilyteline	anonymous	2016-01-07 16:53:43.31696	50	\N
22	valeperspektiivi	anonymous	2016-01-07 16:59:14.970948	50	\N
23	näkölinja	anonymous	2016-01-07 16:59:14.990645	50	\N
24	peffaliuku	anonymous	2016-01-07 17:02:32.777667	50	\N
25	taikapolku	anonymous	2016-01-07 17:22:25.090796	50	\N
26	pitkokset	anonymous	2016-01-07 17:22:25.111538	50	\N
27	maja	anonymous	2016-01-07 17:34:50.358978	50	\N
28	romahtanut	anonymous	2016-01-07 17:34:50.378787	50	\N
29	hajuviemäri	anonymous	2016-01-07 17:35:23.924209	50	\N
30	rakkauden puu	anonymous	2016-01-07 18:22:18.388314	50	\N
31	leikkipuisto	anonymous	2016-01-08 11:30:34.966442	50	\N
32	tanssilava	anonymous	2016-01-11 11:28:34.814666	50	\N
\.


--
-- Name: tag_id_seq; Type: SEQUENCE SET; Schema: public; Owner: atehwa
--

SELECT pg_catalog.setval('tag_id_seq', 32, true);


--
-- Data for Name: webuser; Type: TABLE DATA; Schema: public; Owner: atehwa
--

COPY webuser (id, username, identification, authscheme) FROM stdin;
1	system	\N	none
2	anonymous	\N	any
3	testuser	\N	key
\.


--
-- Name: webuser_id_seq; Type: SEQUENCE SET; Schema: public; Owner: atehwa
--

SELECT pg_catalog.setval('webuser_id_seq', 3, true);


--
-- PostgreSQL database dump complete
--

