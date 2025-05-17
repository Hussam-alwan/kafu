-- Function to populate all tables
CREATE OR REPLACE FUNCTION populate_all_tables()
RETURNS void AS $$
DECLARE
    city_names TEXT[] := ARRAY['DAMASCUS', 'ALEPPO', 'HOMS', 'HAMAH', 'IDLIB',
                              'QUNAITRA', 'DARAA', 'SUWAIDA', 'TARTUS', 'LATAKIA'];
BEGIN
   -- Populate Address
   INSERT INTO Address (id, latitude, longitude, description, city) VALUES
   (1, 33.5138, 36.2765, 'شارع بغداد، بالقرب من المركز الثقافي الروسي', 'DAMASCUS'),
   (2, 33.7206, 36.5662, 'منطقة جرمانا، خلف مطعم الراعي', 'REEF_DAM'),
   (3, 36.2154, 37.1596, 'المدينة الجامعية، مبنى الاقتصاد', 'ALEPPO'),
   (4, 34.7304, 36.7097, 'حي عكرمة، قرب جامع الرحمة', 'HOMS'),
   (5, 35.1324, 36.7528, 'ساحة العاصي، بجانب دار الكتب الوطنية', 'HAMAH'),
   (6, 35.9306, 36.6339, 'شارع الثلاثين، بالقرب من مشفى الهلال الأحمر', 'IDLIB'),
   (7, 33.1264, 35.8213, 'مدينة البعث، مقابل المصرف العقاري', 'QUNAITRA'),
   (8, 32.6189, 36.1051, 'طريق السد، مقابل معهد الحاسوب', 'DARAA'),
   (9, 32.7093, 36.5668, 'حي الجلاء، خلف كراج الانطلاق', 'SUWAIDA'),
   (10, 35.0739, 35.9735, 'الكورنيش البحري، قرب دوار الزراعة', 'TARTUS'),
   (11, 35.5195, 35.7916, 'الشيخ ضاهر، خلف الحديقة العامة', 'LATAKIA'),
   (12, 35.9594, 39.0068, 'شارع تل أبيض، بجانب جامع الكبير', 'RAQQA'),
   (13, 35.3366, 40.1419, 'حي الجورة، مقابل مشفى الأسد', 'DEER'),
   (14, 36.5021, 40.7474, 'مركز المدينة، بجوار شركة الكهرباء', 'HASAKA'),
   (15, 33.5122, 36.3026, 'ركن الدين، جانب مدرسة زينب الهلالية', 'DAMASCUS'),
   (16, 33.6205, 36.2843, 'صحنايا، خلف دوار المواصلات', 'REEF_DAM'),
   (17, 36.1939, 37.1343, 'السبيل، أمام حديقة الحيوان', 'ALEPPO'),
   (18, 34.7275, 36.7133, 'بابا عمرو، مقابل المركز الصحي', 'HOMS'),
   (19, 35.1360, 36.7510, 'حي الصابونية، خلف مدرسة الصناعة', 'HAMAH'),
   (20, 35.9311, 36.6327, 'محيط دوار الزراعة، قرب سوبر ماركت النور', 'IDLIB'),
   (21, 33.1102, 35.8301, 'الحرية، شارع المدارس', 'QUNAITRA'),
   (22, 32.6171, 36.1045, 'الضاحية، بجانب مركز الاتصالات', 'DARAA'),
   (23, 32.7105, 36.5679, 'حي المطار، جانب صالة النخيل', 'SUWAIDA'),
   (24, 35.0730, 35.9739, 'المدينة القديمة، خلف القلعة', 'TARTUS'),
   (25, 35.5192, 35.7920, 'الطابيات، بجانب المدرسة الثانوية', 'LATAKIA'),
	(26, 33.5150, 36.2800, 'شارع الثورة، بجانب البنك التجاري السوري', 'DAMASCUS'),
	(27, 33.7250, 36.5700, 'حي المزة، مقابل حديقة العامرية', 'REEF_DAM'),
	(28, 36.2200, 37.1650, 'شارع السليمانية، قرب مستشفى الجامعة', 'ALEPPO'),
	(29, 34.7350, 36.7150, 'حي الاندلس، بجوار مدرسة الباسل', 'HOMS'),
	(30, 35.1350, 36.7550, 'شارع القوتلي، مقابل دار الثقافة', 'HAMAH'),
	(31, 35.9350, 36.6350, 'حي الصفصافة، قرب مركز الشرطة', 'IDLIB'),
	(32, 33.1300, 35.8250, 'شارع النصر، بجانب الصيدلية الوطنية', 'QUNAITRA'),
	(33, 32.6200, 36.1100, 'حي الفيحاء، مقابل المدرسة الابتدائية', 'DARAA'),
	(34, 32.7150, 36.5700, 'شارع الثورة، قرب سوق الخضار المركزي', 'SUWAIDA'),
	(35, 35.0750, 35.9750, 'حي الضاحية، بجوار نادي الضباط', 'TARTUS'),
	(36, 35.5200, 35.7950, 'شارع 8 آذار، مقابل مكتبة الأسد', 'LATAKIA'),
	(37, 35.9600, 39.0100, 'حي التضامن، قرب مركز البريد', 'RAQQA'),
	(38, 35.3400, 40.1450, 'شارع النهضة، بجانب صالة الأفراح', 'DEER'),
	(39, 36.5050, 40.7500, 'حي العزيزية، مقابل المدرسة الثانوية', 'HASAKA'),
	(40, 33.5130, 36.3050, 'شارع بغداد، قرب مقهى الشام', 'DAMASCUS'),
	(41, 33.6250, 36.2850, 'حي المالكي، بجوار صيدلية الحياة', 'REEF_DAM'),
	(42, 36.1950, 37.1350, 'شارع بارون، مقابل البنك الزراعي', 'ALEPPO'),
	(43, 34.7300, 36.7150, 'حي الحمراء، قرب سوق الخضار', 'HOMS'),
	(44, 35.1370, 36.7520, 'شارع العروبة، بجانب صالون الحلاقة', 'HAMAH'),
	(45, 35.9320, 36.6330, 'حي القصور، مقابل المدرسة الابتدائية', 'IDLIB'),
	(46, 33.1150, 35.8350, 'شارع الجلاء، قرب مخبز الوطن', 'QUNAITRA'),
	(47, 32.6180, 36.1050, 'حي الأندلس، بجوار مركز الشرطة', 'DARAA'),
	(48, 32.7150, 36.5700, 'شارع النصر، مقابل الصيدلية الكبرى', 'SUWAIDA'),
	(49, 35.0740, 35.9740, 'حي الصفصافة، قرب نادي المعلمين', 'TARTUS'),
	(50, 35.5200, 35.7930, 'شارع الجمهورية، بجانب البنك العقاري', 'LATAKIA'),
	(51, 33.5160, 36.2810, 'حي المهاجرين، قرب مستشفى المواساة', 'DAMASCUS'),
	(52, 33.7260, 36.5710, 'شارع المركز، مقابل مدرسة البنين', 'REEF_DAM'),
	(53, 36.2160, 37.1600, 'حي السريان، بجوار كنيسة مار جرجس', 'ALEPPO'),
	(54, 34.7360, 36.7160, 'شارع الحكمة، قرب صيدلية النور', 'HOMS'),
	(55, 35.1330, 36.7530, 'حي القصور، مقابل مقهى الشهداء', 'HAMAH'),
	(56, 35.9360, 36.6360, 'شارع النهضة، بجانب مركز الاتصالات', 'IDLIB'),
	(57, 33.1270, 35.8220, 'حي البعث، قرب مدرسة البنات', 'QUNAITRA'),
	(58, 32.6190, 36.1060, 'شارع الثورة، مقابل صالون الحلاقة', 'DARAA'),
	(59, 32.7110, 36.5680, 'حي الرياض، بجوار الملعب البلدي', 'SUWAIDA'),
	(60, 35.0760, 35.9760, 'شارع الكورنيش، قرب مطعم السمك', 'TARTUS'),
	(61, 35.5210, 35.7960, 'حي الجامعة، مقابل المكتبة العامة', 'LATAKIA'),
	(62, 35.9610, 39.0070, 'شارع النيل، بجانب مركز الشرطة', 'RAQQA'),
	(63, 35.3370, 40.1420, 'حي العروبة، قرب سوق الخضار', 'DEER'),
	(64, 36.5030, 40.7480, 'شارع النصر، مقابل البنك الزراعي', 'HASAKA'),
	(65, 33.5140, 36.3030, 'حي الشهداء، بجوار صيدلية الحياة', 'DAMASCUS'),
	(66, 33.6210, 36.2850, 'شارع الجلاء، قرب مخبز الشام', 'REEF_DAM'),
	(67, 36.1940, 37.1350, 'حي التضامن، مقابل صالون الحلاقة', 'ALEPPO'),
	(68, 34.7280, 36.7140, 'شارع العروبة، بجانب مركز الاتصالات', 'HOMS'),
	(69, 35.1370, 36.7530, 'حي الصناعة، قرب المدرسة الفنية', 'HAMAH'),
	(70, 35.9320, 36.6340, 'شارع المركز، مقابل الصيدلية الكبرى', 'IDLIB'),
	(71, 33.1110, 35.8310, 'حي النصر، بجوار الملعب البلدي', 'QUNAITRA'),
	(72, 32.6180, 36.1050, 'شارع السد، قرب مركز البريد', 'DARAA'),
	(73, 32.7110, 36.5690, 'حي المطار، مقابل مدرسة البنين', 'SUWAIDA'),
	(74, 35.0740, 35.9750, 'شارع القدس، بجانب نادي الضباط', 'TARTUS'),
	(75, 35.5200, 35.7940, 'حي الطابيات، قرب صيدلية النور', 'LATAKIA'),
	(76, 33.5150, 36.2820, 'شارع بغداد، مقابل البنك التجاري', 'DAMASCUS'),
	(77, 33.7240, 36.5670, 'حي المزة، بجوار حديقة العامرية', 'REEF_DAM'),
	(78, 36.2170, 37.1610, 'شارع السليمانية، قرب مستشفى الجامعة', 'ALEPPO'),
	(79, 34.7340, 36.7150, 'حي الاندلس، مقابل مدرسة الباسل', 'HOMS'),
	(80, 35.1340, 36.7540, 'شارع القوتلي، بجانب دار الثقافة', 'HAMAH'),
	(81, 35.9340, 36.6340, 'حي الصفصافة، مقابل مركز الشرطة', 'IDLIB'),
	(82, 33.1290, 35.8240, 'شارع النصر، قرب الصيدلية الوطنية', 'QUNAITRA'),
	(83, 32.6190, 36.1090, 'حي الفيحاء، بجوار المدرسة الابتدائية', 'DARAA'),
	(84, 32.7140, 36.5690, 'شارع الثورة، مقابل سوق الخضار', 'SUWAIDA'),
	(85, 35.0760, 35.9740, 'حي الضاحية، قرب نادي الضباط', 'TARTUS'),
	(86, 35.5210, 35.7920, 'شارع 8 آذار، بجانب مكتبة الأسد', 'LATAKIA'),
	(87, 35.9620, 39.0080, 'حي التضامن، مقابل مركز البريد', 'RAQQA'),
	(88, 35.3390, 40.1440, 'شارع النهضة، قرب صالة الأفراح', 'DEER'),
	(89, 36.5040, 40.7490, 'حي العزيزية، بجوار المدرسة الثانوية', 'HASAKA'),
	(90, 33.5120, 36.3040, 'شارع بغداد، مقابل مقهى الشام', 'DAMASCUS'),
	(91, 33.6240, 36.2860, 'حي المالكي، قرب صيدلية الحياة', 'REEF_DAM'),
	(92, 36.1960, 37.1360, 'شارع بارون، بجانب البنك الزراعي', 'ALEPPO'),
	(93, 34.7290, 36.7160, 'حي الحمراء، مقابل سوق الخضار', 'HOMS'),
	(94, 35.1380, 36.7540, 'شارع العروبة، قرب صالون الحلاقة', 'HAMAH'),
	(95, 35.9330, 36.6350, 'حي القصور، بجوار المدرسة الابتدائية', 'IDLIB');

	ALTER SEQUENCE address_seq RESTART WITH 100;

    -- Populate Gov
    -- Reset sequence to start after manual IDs (1-21)


    -- Insert main ministries (21 ministries)
    INSERT INTO Gov (id, name, email, logo_url, address_id, phone, parent_gov_id) VALUES
    (1, 'وزارة الطاقة', 'energy@government.gov', 'https://picsum.photos/200/300', 1, '+963112345601', NULL),
    (2, 'وزارة العدل', 'justice@government.gov', 'https://picsum.photos/200/301', 2, '+963112345602', NULL),
    (3, 'وزارة الداخلية', 'interior@government.gov', 'https://picsum.photos/200/302', 3, '+963112345603', NULL),
    (4, 'وزارة المالية', 'finance@government.gov', 'https://picsum.photos/200/303', 4, '+963112345604', NULL),
    (5, 'وزارة الاقتصاد والصناعة', 'economy@government.gov', 'https://picsum.photos/200/304', 5, '+963112345605', NULL),
    (6, 'وزارة الأوقاف', 'endowments@government.gov', 'https://picsum.photos/200/305', 6, '+963112345606', NULL),
    (7, 'وزارة التربية', 'education@government.gov', 'https://picsum.photos/200/306', 7, '+963112345607', NULL),
    (8, 'وزارة التعليم العالي', 'highereducation@government.gov', 'https://picsum.photos/200/307', 8, '+963112345608', NULL),
    (9, 'وزارة الصحة', 'health@government.gov', 'https://picsum.photos/200/308', 9, '+963112345609', NULL),
    (10, 'وزارة الشؤون الاجتماعية والعمل', 'social@government.gov', 'https://picsum.photos/200/309', 10, '+963112345610', NULL),
    (11, 'وزارة الدفاع', 'defense@government.gov', 'https://picsum.photos/200/310', 11, '+963112345611', NULL),
    (12, 'وزارة الإدارة المحلية والبيئة', 'localadmin@government.gov', 'https://picsum.photos/200/311', 12, '+963112345612', NULL),
    (13, 'وزارة الاتصالات وتقانة المعلومات', 'communications@government.gov', 'https://picsum.photos/200/312', 13, '+963112345613', NULL),
    (14, 'وزارة النقل', 'transport@government.gov', 'https://picsum.photos/200/313', 14, '+963112345614', NULL),
    (15, 'وزارة الزراعة', 'agriculture@government.gov', 'https://picsum.photos/200/314', 15, '+963112345615', NULL),
    (16, 'وزارة الأشغال العامة والإسكان', 'publicworks@government.gov', 'https://picsum.photos/200/315', 16, '+963112345616', NULL),
    (17, 'وزارة الثقافة', 'culture@government.gov', 'https://picsum.photos/200/316', 17, '+963112345617', NULL),
    (18, 'وزارة السياحة', 'tourism@government.gov', 'https://picsum.photos/200/317', 18, '+963112345618', NULL),
    (19, 'وزارة الرياضة والشباب', 'sports@government.gov', 'https://picsum.photos/200/318', 19, '+963112345619', NULL),
    (20, 'وزارة التنمية الإدارية', 'admindevelopment@government.gov', 'https://picsum.photos/200/319', 20, '+963112345620', NULL),
    (21, 'وزارة الطوارئ والكوارث', 'emergency@government.gov', 'https://picsum.photos/200/320', 21, '+963112345621', NULL);

    -- Insert sub-ministries (3 per ministry)
	INSERT INTO Gov (id, name, email, logo_url, address_id, phone, parent_gov_id) VALUES
	-- وزارة الطاقة (1)
	(22, 'هيئة الكهرباء', 'electricity@energy.gov', 'https://picsum.photos/200/321', 22, '+963112345701', 1),
	(23, 'هيئة النفط', 'oil@energy.gov', 'https://picsum.photos/200/322', 23, '+963112345702', 1),
	(24, 'هيئة الطاقة المتجددة', 'renewable@energy.gov', 'https://picsum.photos/200/323', 24, '+963112345703', 1),

	-- وزارة العدل (2)
	(25, 'المحكمة الدستورية العليا', 'constitutionalcourt@justice.gov', 'https://picsum.photos/200/324', 25, '+963112345704', 2),
	(26, 'هيئة القضاء', 'judiciary@justice.gov', 'https://picsum.photos/200/325', 26, '+963112345705', 2),
	(27, 'هيئة النيابة العامة', 'prosecution@justice.gov', 'https://picsum.photos/200/326', 27, '+963112345706', 2),

	-- وزارة الداخلية (3)
	(28, 'مديرية الأمن العام', 'security@interior.gov', 'https://picsum.photos/200/327', 28, '+963112345707', 3),
	(29, 'مديرية المرور', 'traffic@interior.gov', 'https://picsum.photos/200/328', 29, '+963112345708', 3),
	(30, 'مديرية الجوازات', 'passports@interior.gov', 'https://picsum.photos/200/329', 30, '+963112345709', 3),

	-- وزارة المالية (4)
	(31, 'هيئة الضرائب', 'taxes@finance.gov', 'https://picsum.photos/200/330', 31, '+963112345710', 4),
	(32, 'هيئة الجمارك', 'customs@finance.gov', 'https://picsum.photos/200/331', 32, '+963112345711', 4),
	(33, 'البنك المركزي', 'centralbank@finance.gov', 'https://picsum.photos/200/332', 33, '+963112345712', 4),

	-- وزارة الاقتصاد والصناعة (5)
	(34, 'هيئة الاستثمار', 'investment@economy.gov', 'https://picsum.photos/200/333', 34, '+963112345713', 5),
	(35, 'هيئة المواصفات والمقاييس', 'standards@economy.gov', 'https://picsum.photos/200/334', 35, '+963112345714', 5),
	(36, 'غرفة الصناعة', 'industry@economy.gov', 'https://picsum.photos/200/335', 36, '+963112345715', 5),

	-- وزارة الأوقاف (6)
	(37, 'هيئة المساجد', 'mosques@endowments.gov', 'https://picsum.photos/200/336', 37, '+963112345716', 6),
	(38, 'هيئة الأوقاف الإسلامية', 'islamicendowments@endowments.gov', 'https://picsum.photos/200/337', 38, '+963112345717', 6),
	(39, 'هيئة الدعوة الدينية', 'religiouspreaching@endowments.gov', 'https://picsum.photos/200/338', 39, '+963112345718', 6),

	-- وزارة التربية (7)
	(40, 'مديرية التعليم الأساسي', 'basiceducation@education.gov', 'https://picsum.photos/200/339', 40, '+963112345719', 7),
	(41, 'مديرية التعليم الثانوي', 'secondaryeducation@education.gov', 'https://picsum.photos/200/340', 41, '+963112345720', 7),
	(42, 'هيئة الامتحانات', 'examinations@education.gov', 'https://picsum.photos/200/341', 42, '+963112345721', 7),

	-- وزارة التعليم العالي (8)
	(43, 'هيئة الاعتماد والجودة', 'accreditation@highereducation.gov', 'https://picsum.photos/200/342', 43, '+963112345722', 8),
	(44, 'هيئة البحث العلمي', 'research@highereducation.gov', 'https://picsum.photos/200/343', 44, '+963112345723', 8),
	(45, 'المجلس الأعلى للجامعات', 'universities@highereducation.gov', 'https://picsum.photos/200/344', 45, '+963112345724', 8),

	-- وزارة الصحة (9)
	(46, 'هيئة المستشفيات', 'hospitals@health.gov', 'https://picsum.photos/200/345', 46, '+963112345725', 9),
	(47, 'هيئة الرعاية الصحية الأولية', 'primarycare@health.gov', 'https://picsum.photos/200/346', 47, '+963112345726', 9),
	(48, 'هيئة الغذاء والدواء', 'fda@health.gov', 'https://picsum.photos/200/347', 48, '+963112345727', 9),

	-- وزارة الشؤون الاجتماعية والعمل (10)
	(49, 'هيئة الضمان الاجتماعي', 'socialsecurity@social.gov', 'https://picsum.photos/200/348', 49, '+963112345728', 10),
	(50, 'هيئة العمل والتشغيل', 'employment@social.gov', 'https://picsum.photos/200/349', 50, '+963112345729', 10),
	(51, 'هيئة الرعاية الاجتماعية', 'welfare@social.gov', 'https://picsum.photos/200/350', 51, '+963112345730', 10),

	-- وزارة الدفاع (11)
	(52, 'القوات البرية', 'army@defense.gov', 'https://picsum.photos/200/351', 52, '+963112345731', 11),
	(53, 'القوات الجوية', 'airforce@defense.gov', 'https://picsum.photos/200/352', 53, '+963112345732', 11),
	(54, 'القوات البحرية', 'navy@defense.gov', 'https://picsum.photos/200/353', 54, '+963112345733', 11),

	-- وزارة الإدارة المحلية والبيئة (12)
	(55, 'هيئة البلديات', 'municipalities@localadmin.gov', 'https://picsum.photos/200/354', 55, '+963112345734', 12),
	(56, 'هيئة حماية البيئة', 'environment@localadmin.gov', 'https://picsum.photos/200/355', 56, '+963112345735', 12),
	(57, 'هيئة التخطيط العمراني', 'urbanplanning@localadmin.gov', 'https://picsum.photos/200/356', 57, '+963112345736', 12),

	-- وزارة الاتصالات وتقانة المعلومات (13)
	(58, 'هيئة الاتصالات', 'telecom@communications.gov', 'https://picsum.photos/200/357', 58, '+963112345737', 13),
	(59, 'هيئة البريد', 'post@communications.gov', 'https://picsum.photos/200/358', 59, '+963112345738', 13),
	(60, 'هيئة المعلوماتية', 'informatics@communications.gov', 'https://picsum.photos/200/359', 60, '+963112345739', 13),

	-- وزارة النقل (14)
	(61, 'هيئة الطرق', 'roads@transport.gov', 'https://picsum.photos/200/360', 61, '+963112345740', 14),
	(62, 'هيئة النقل البري', 'landtransport@transport.gov', 'https://picsum.photos/200/361', 62, '+963112345741', 14),
	(63, 'هيئة النقل الجوي', 'airtransport@transport.gov', 'https://picsum.photos/200/362', 63, '+963112345742', 14),

	-- وزارة الزراعة (15)
	(64, 'هيئة الري', 'irrigation@agriculture.gov', 'https://picsum.photos/200/363', 64, '+963112345743', 15),
	(65, 'هيئة الثروة الحيوانية', 'livestock@agriculture.gov', 'https://picsum.photos/200/364', 65, '+963112345744', 15);

	ALTER SEQUENCE gov_seq RESTART WITH 70;



   -- Populate Users (50 records)
    INSERT INTO Users (id, keycloak_id, first_name, last_name, email, phone, date_of_birth,
                      college_degree, job, cv_url, photo_url, description, address_id, gov_id,deleted)
    SELECT
        i,
        'kcid-' || i,
        'FirstName' || i,
        'LastName' || i,
        'user' || i || '@email.com',
        '+1' || (4000000000 + (i * 22222))::TEXT,
        date '1990-01-01' + (i || ' days')::interval,
        CASE WHEN i % 2 = 0 THEN 'BSc Computer Science' ELSE 'MBA' END,
        (ARRAY['Engineer','Manager','Analyst','Developer'])[(i % 4) + 1],
        'http://cv.example.com/' || i,
        'http://photos.example.com/' || i || '.jpg',
        'User description for ' || i,
        a.id,
        g.id,
        null
    FROM generate_series(1, 50) i
    JOIN Address a ON a.id = i
    JOIN Gov g ON g.id = i;

	ALTER SEQUENCE users_seq RESTART WITH 70;

    -- Populate Problem_Category (50 records)
    INSERT INTO Problem_Category (id, name, gov_id)
    SELECT
        i,
        'Category ' || (i % 5 + 1) || '-' || i,
        g.id
    FROM generate_series(1, 50) i
    JOIN Gov g ON g.id = i;

	ALTER SEQUENCE problem_category_seq RESTART WITH 55;

    -- Populate Problem (50 records)
    INSERT INTO Problem (id, title, description, is_real, for_contribution, for_donation,
                        submission_date, submitted_by_user_id, approved_by_user_id,
                        rejection_reason, address_id, category_id, status)
    SELECT
        i,
        'المشكلة رقم ' || i || ': ' || (ARRAY['حفرة','اضاءة مكسورة','مستلزمات مدرسية','شارع متسخ','تسريب مياه'])[(i % 5) + 1],
        'تفاصيل المشكلة رقم ' || i,
        i % 3 != 0,
        i % 2 = 0,
        i % 4 = 0,
        now() - (i || ' hours')::interval,
        u.id,
        CASE WHEN i % 4 = 0 THEN NULL ELSE (SELECT id FROM Users ORDER BY random() LIMIT 1) END,
        CASE WHEN i % 10 = 0 THEN 'Duplicate entry' ELSE NULL END,
        a.id,
        pc.id,
        (ARRAY['PENDING_APPROVAL','APPROVED','REJECTED','IN_PROGRESS','RESOLVED'])[(i % 5) + 1]
    FROM generate_series(1, 50) i
    JOIN Address a ON a.id = i
    JOIN Problem_Category pc ON pc.id = i
    JOIN Users u ON u.id = i;

	ALTER SEQUENCE problem_seq RESTART WITH 55;

    -- Populate Problem_Photo (50 records)
    INSERT INTO Problem_Photo (id, problem_id, s3_Key, photo_date)
    SELECT
        i,
        p.id,
        'problem/' || i ,
        now() - (i || ' hours')::interval
    FROM generate_series(1, 50) i
    JOIN Problem p ON p.id = i;

	ALTER SEQUENCE problem_photo_seq RESTART WITH 55;

    -- Populate Solution (50 records)
    INSERT INTO Solution (id, description, estimated_cost, status, accepted_reason,
                         start_date, end_date, feedback, rating, problem_id,
                         proposed_by_user_id, accepted_by_user_id)
    SELECT
        i,
        'الحل المقترح رقم ' || i,
        (1000 + (random() * 99000))::numeric(12,2),
        (ARRAY['PENDING_APPROVAL','ACCEPTED','REJECTED','WORKINPROGRESS'])[(i % 4) + 1],
        CASE WHEN i % 4 = 1 THEN 'سعر مناسب' ELSE NULL END,
        current_date + (i || ' days')::interval,
        current_date + ((i + 7) || ' days')::interval,
        CASE WHEN i % 3 = 0 THEN 'اداء جيد' ELSE NULL END,
        CASE WHEN i % 2 = 0 THEN (i % 10) + 1 ELSE NULL END,
        p.id,
        u.id,
        CASE WHEN i % 3 = 0 THEN (SELECT id FROM Users ORDER BY random() LIMIT 1) ELSE NULL END
    FROM generate_series(1, 50) i
    JOIN Problem p ON p.id = i
    JOIN Users u ON u.id = i;

	ALTER SEQUENCE solution_seq RESTART WITH 55;

    -- Populate Donation (50 records)
    INSERT INTO Donation (id, problem_id, donor_id, amount, currency, payment_method,
                         payment_transaction_id, status, is_anonymous, donation_date)
    SELECT
        i,
        p.id,
        u.id,
        (50 + (random() * 4950))::numeric(12,2),
        CASE WHEN i % 3 = 0 THEN 'EUR' ELSE 'USD' END,
        'STRIPE',
        'txn_' || md5(random()::text),
        (ARRAY['CREATED', 'SUCCESS','FAILED'])[(i % 3) + 1],
        i % 4 = 0,
        now() - (i || ' hours')::interval
    FROM generate_series(1, 50) i
    JOIN Problem p ON p.id = i
    JOIN Users u ON u.id = i;

	ALTER SEQUENCE donation_seq RESTART WITH 55;

    -- Populate Problem_Progress (50 records)
    INSERT INTO Problem_Progress (id, percentage, comment, progress_date, problem_id, solution_id)
    SELECT
        i,
        (i * 2) % 100,
        'Progress update ' || i,
        now() - (i || ' hours')::interval,
        p.id,
        s.id
    FROM generate_series(1, 50) i
    JOIN Problem p ON p.id = i
    JOIN Solution s ON s.id = i;

	ALTER SEQUENCE problem_progress_seq RESTART WITH 55;

END;
$$ LANGUAGE plpgsql;

-- Execute the function
SELECT populate_all_tables();