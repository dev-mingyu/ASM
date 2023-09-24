-- Insert into CVE table
INSERT INTO CVE (CVE_CODE) VALUES ('CVE-2022-21654');
INSERT INTO CVE (CVE_CODE) VALUES ('CVE-2021-23383');
INSERT INTO CVE (CVE_CODE) VALUES ('CVE-2021-233T0');
INSERT INTO CVE (CVE_CODE) VALUES ('CVE-2021-23369');
INSERT INTO CVE (CVE_CODE) VALUES ('CVE-2021-22931');
INSERT INTO CVE (CVE_CODE) VALUES ('CVE-2021-22930');

-- Assuming the IDs are auto-incremented starting from 1
-- Insert into cve_softwares table for each software related to a CVE code
INSERT INTO cve_softwares (cve_id, software) VALUES (1, 'envoy'), (1, 'ios');
INSERT INTO cve_softwares (cve_id, software) VALUES (2, 'tomcat'), (2, 'apache');
INSERT INTO cve_softwares (cve_id, software) VALUES (3, 'vue.js'), (3, 'react');
INSERT INTO cve_softwares (cve_id, software) VALUES (4, 'java'), (4, 'spring');
INSERT INTO cve_softwares (cve_id, software) VALUES (5, 'node.js'), (5, 'javascript');
INSERT INTO cve_softwares (cve_id, software) VALUES (6, 'nginx');
