-- Function to populate all tables
CREATE OR REPLACE FUNCTION populate_all_tables()
RETURNS void AS $$
DECLARE
    city_names TEXT[] := ARRAY['DAMASCUS', 'ALEPPO', 'HOMS', 'HAMAH', 'IDLIB',
                              'QUNAITRA', 'DARAA', 'SUWAIDA', 'TARTUS', 'LATAKIA'];
BEGIN
    INSERT INTO Address (id, latitude, longitude, description, city) VALUES
        (nextval('address_seq'), 33.5138, 36.2765, 'شارع بغداد، بالقرب من المركز الثقافي الروسي', 'DAMASCUS'),
        (nextval('address_seq'), 33.7206, 36.5662, 'منطقة جرمانا، خلف مطعم الراعي', 'REEF_DAM'),
        (nextval('address_seq'), 36.2154, 37.1596, 'المدينة الجامعية، مبنى الاقتصاد', 'ALEPPO'),
        (nextval('address_seq'), 34.7304, 36.7097, 'حي عكرمة، قرب جامع الرحمة', 'HOMS'),
        (nextval('address_seq'), 35.1324, 36.7528, 'ساحة العاصي، بجانب دار الكتب الوطنية', 'HAMAH'),
        (nextval('address_seq'), 35.9306, 36.6339, 'شارع الثلاثين، بالقرب من مشفى الهلال الأحمر', 'IDLIB'),
        (nextval('address_seq'), 33.1264, 35.8213, 'مدينة البعث، مقابل المصرف العقاري', 'QUNAITRA'),
        (nextval('address_seq'), 32.6189, 36.1051, 'طريق السد، مقابل معهد الحاسوب', 'DARAA'),
        (nextval('address_seq'), 32.7093, 36.5668, 'حي الجلاء، خلف كراج الانطلاق', 'SUWAIDA'),
        (nextval('address_seq'), 35.0739, 35.9735, 'الكورنيش البحري، قرب دوار الزراعة', 'TARTUS'),
        (nextval('address_seq'), 35.5195, 35.7916, 'الشيخ ضاهر، خلف الحديقة العامة', 'LATAKIA'),
        (nextval('address_seq'), 35.9594, 39.0068, 'شارع تل أبيض، بجانب جامع الكبير', 'RAQQA'),
        (nextval('address_seq'), 35.3366, 40.1419, 'حي الجورة، مقابل مشفى الأسد', 'DEER'),
        (nextval('address_seq'), 36.5021, 40.7474, 'مركز المدينة، بجوار شركة الكهرباء', 'HASAKA'),
        (nextval('address_seq'), 33.5122, 36.3026, 'ركن الدين، جانب مدرسة زينب الهلالية', 'DAMASCUS'),
        (nextval('address_seq'), 33.6205, 36.2843, 'صحنايا، خلف دوار المواصلات', 'REEF_DAM'),
        (nextval('address_seq'), 36.1939, 37.1343, 'السبيل، أمام حديقة الحيوان', 'ALEPPO'),
        (nextval('address_seq'), 34.7275, 36.7133, 'بابا عمرو، مقابل المركز الصحي', 'HOMS'),
        (nextval('address_seq'), 35.1360, 36.7510, 'حي الصابونية، خلف مدرسة الصناعة', 'HAMAH'),
        (nextval('address_seq'), 35.9311, 36.6327, 'محيط دوار الزراعة، قرب سوبر ماركت النور', 'IDLIB'),
        (nextval('address_seq'), 33.1102, 35.8301, 'الحرية، شارع المدارس', 'QUNAITRA'),
        (nextval('address_seq'), 32.6171, 36.1045, 'الضاحية، بجانب مركز الاتصالات', 'DARAA'),
        (nextval('address_seq'), 32.7105, 36.5679, 'حي المطار، جانب صالة النخيل', 'SUWAIDA'),
        (nextval('address_seq'), 35.0730, 35.9739, 'المدينة القديمة، خلف القلعة', 'TARTUS'),
        (nextval('address_seq'), 35.5192, 35.7920, 'الطابيات، بجانب المدرسة الثانوية', 'LATAKIA'),
        (nextval('address_seq'), 33.5150, 36.2800, 'شارع الثورة، بجانب البنك التجاري السوري', 'DAMASCUS'),
        (nextval('address_seq'), 33.7250, 36.5700, 'حي المزة، مقابل حديقة العامرية', 'REEF_DAM'),
        (nextval('address_seq'), 36.2200, 37.1650, 'شارع السليمانية، قرب مستشفى الجامعة', 'ALEPPO'),
        (nextval('address_seq'), 34.7350, 36.7150, 'حي الاندلس، بجوار مدرسة الباسل', 'HOMS'),
        (nextval('address_seq'), 35.1350, 36.7550, 'شارع القوتلي، مقابل دار الثقافة', 'HAMAH'),
        (nextval('address_seq'), 35.9350, 36.6350, 'حي الصفصافة، قرب مركز الشرطة', 'IDLIB'),
        (nextval('address_seq'), 33.1300, 35.8250, 'شارع النصر، بجانب الصيدلية الوطنية', 'QUNAITRA'),
        (nextval('address_seq'), 32.6200, 36.1100, 'حي الفيحاء، مقابل المدرسة الابتدائية', 'DARAA'),
        (nextval('address_seq'), 32.7150, 36.5700, 'شارع الثورة، قرب سوق الخضار المركزي', 'SUWAIDA'),
        (nextval('address_seq'), 35.0750, 35.9750, 'حي الضاحية، بجوار نادي الضباط', 'TARTUS'),
        (nextval('address_seq'), 35.5200, 35.7950, 'شارع 8 آذار، مقابل مكتبة الأسد', 'LATAKIA'),
        (nextval('address_seq'), 35.9600, 39.0100, 'حي التضامن، قرب مركز البريد', 'RAQQA'),
        (nextval('address_seq'), 35.3400, 40.1450, 'شارع النهضة، بجانب صالة الأفراح', 'DEER'),
        (nextval('address_seq'), 36.5050, 40.7500, 'حي العزيزية، مقابل المدرسة الثانوية', 'HASAKA'),
        (nextval('address_seq'), 33.5130, 36.3050, 'شارع بغداد، قرب مقهى الشام', 'DAMASCUS'),
        (nextval('address_seq'), 33.6250, 36.2850, 'حي المالكي، بجوار صيدلية الحياة', 'REEF_DAM'),
        (nextval('address_seq'), 36.1950, 37.1350, 'شارع بارون، مقابل البنك الزراعي', 'ALEPPO'),
        (nextval('address_seq'), 34.7300, 36.7150, 'حي الحمراء، قرب سوق الخضار', 'HOMS'),
        (nextval('address_seq'), 35.1370, 36.7520, 'شارع العروبة، بجانب صالون الحلاقة', 'HAMAH'),
        (nextval('address_seq'), 35.9320, 36.6330, 'حي القصور، مقابل المدرسة الابتدائية', 'IDLIB'),
        (nextval('address_seq'), 33.1150, 35.8350, 'شارع الجلاء، قرب مخبز الوطن', 'QUNAITRA'),
        (nextval('address_seq'), 32.6180, 36.1050, 'حي الأندلس، بجوار مركز الشرطة', 'DARAA'),
        (nextval('address_seq'), 32.7150, 36.5700, 'شارع النصر، مقابل الصيدلية الكبرى', 'SUWAIDA'),
        (nextval('address_seq'), 35.0740, 35.9740, 'حي الصفصافة، قرب نادي المعلمين', 'TARTUS'),
        (nextval('address_seq'), 35.5200, 35.7930, 'شارع الجمهورية، بجانب البنك العقاري', 'LATAKIA'),
        (nextval('address_seq'), 33.5160, 36.2810, 'حي المهاجرين، قرب مستشفى المواساة', 'DAMASCUS'),
        (nextval('address_seq'), 33.7260, 36.5710, 'شارع المركز، مقابل مدرسة البنين', 'REEF_DAM'),
        (nextval('address_seq'), 36.2160, 37.1600, 'حي السريان، بجوار كنيسة مار جرجس', 'ALEPPO'),
        (nextval('address_seq'), 34.7360, 36.7160, 'شارع الحكمة، قرب صيدلية النور', 'HOMS'),
        (nextval('address_seq'), 35.1330, 36.7530, 'حي القصور، مقابل مقهى الشهداء', 'HAMAH'),
        (nextval('address_seq'), 35.9360, 36.6360, 'شارع النهضة، بجانب مركز الاتصالات', 'IDLIB'),
        (nextval('address_seq'), 33.1270, 35.8220, 'حي البعث، قرب مدرسة البنات', 'QUNAITRA'),
        (nextval('address_seq'), 32.6190, 36.1060, 'شارع الثورة، مقابل صالون الحلاقة', 'DARAA'),
        (nextval('address_seq'), 32.7110, 36.5680, 'حي الرياض، بجوار الملعب البلدي', 'SUWAIDA'),
        (nextval('address_seq'), 35.0760, 35.9760, 'شارع الكورنيش، قرب مطعم السمك', 'TARTUS'),
        (nextval('address_seq'), 35.5210, 35.7960, 'حي الجامعة، مقابل المكتبة العامة', 'LATAKIA'),
        (nextval('address_seq'), 35.9610, 39.0070, 'شارع النيل، بجانب مركز الشرطة', 'RAQQA'),
        (nextval('address_seq'), 35.3370, 40.1420, 'حي العروبة، قرب سوق الخضار', 'DEER'),
        (nextval('address_seq'), 36.5030, 40.7480, 'شارع النصر، مقابل البنك الزراعي', 'HASAKA'),
        (nextval('address_seq'), 33.5140, 36.3030, 'حي الشهداء، بجوار صيدلية الحياة', 'DAMASCUS'),
        (nextval('address_seq'), 33.6210, 36.2850, 'شارع الجلاء، قرب مخبز الشام', 'REEF_DAM'),
        (nextval('address_seq'), 36.1940, 37.1350, 'حي التضامن، مقابل صالون الحلاقة', 'ALEPPO'),
        (nextval('address_seq'), 34.7280, 36.7140, 'شارع العروبة، بجانب مركز الاتصالات', 'HOMS'),
        (nextval('address_seq'), 35.1370, 36.7530, 'حي الصناعة، قرب المدرسة الفنية', 'HAMAH'),
        (nextval('address_seq'), 35.9320, 36.6340, 'شارع المركز، مقابل الصيدلية الكبرى', 'IDLIB'),
        (nextval('address_seq'), 33.1110, 35.8310, 'حي النصر، بجوار الملعب البلدي', 'QUNAITRA'),
        (nextval('address_seq'), 32.6180, 36.1050, 'شارع السد، قرب مركز البريد', 'DARAA'),
        (nextval('address_seq'), 32.7110, 36.5690, 'حي المطار، مقابل مدرسة البنين', 'SUWAIDA'),
        (nextval('address_seq'), 35.0740, 35.9750, 'شارع القدس، بجانب نادي الضباط', 'TARTUS'),
        (nextval('address_seq'), 35.5200, 35.7940, 'حي الطابيات، قرب صيدلية النور', 'LATAKIA'),
        (nextval('address_seq'), 33.5150, 36.2820, 'شارع بغداد، مقابل البنك التجاري', 'DAMASCUS'),
        (nextval('address_seq'), 33.7240, 36.5670, 'حي المزة، بجوار حديقة العامرية', 'REEF_DAM'),
        (nextval('address_seq'), 36.2170, 37.1610, 'شارع السليمانية، قرب مستشفى الجامعة', 'ALEPPO'),
        (nextval('address_seq'), 34.7340, 36.7150, 'حي الاندلس، مقابل مدرسة الباسل', 'HOMS'),
        (nextval('address_seq'), 35.1340, 36.7540, 'شارع القوتلي، بجانب دار الثقافة', 'HAMAH'),
        (nextval('address_seq'), 35.9340, 36.6340, 'حي الصفصافة، مقابل مركز الشرطة', 'IDLIB');

    -- Populate Gov (50 records)
       INSERT INTO Gov (id, name, email, logo_url, address_id, phone, parent_gov_id) VALUES
           (nextval('gov_seq'), 'وزارة الطاقة', 'energy@government.gov', 'https://picsum.photos/200/300', 1, '+963112345601', NULL),
           (nextval('gov_seq'), 'وزارة العدل', 'justice@government.gov', 'https://picsum.photos/200/301', 51, '+963112345602', NULL),
           (nextval('gov_seq'), 'وزارة الداخلية', 'interior@government.gov', 'https://picsum.photos/200/302', 101, '+963112345603', NULL),
           (nextval('gov_seq'), 'وزارة المالية', 'finance@government.gov', 'https://picsum.photos/200/303', 151, '+963112345604', NULL),
           (nextval('gov_seq'), 'وزارة الاقتصاد والصناعة', 'economy@government.gov', 'https://picsum.photos/200/304', 201, '+963112345605', NULL),
           (nextval('gov_seq'), 'وزارة الأوقاف', 'endowments@government.gov', 'https://picsum.photos/200/305', 251, '+963112345606', NULL),
           (nextval('gov_seq'), 'وزارة التربية', 'education@government.gov', 'https://picsum.photos/200/306', 301, '+963112345607', NULL),
           (nextval('gov_seq'), 'وزارة التعليم العالي', 'highereducation@government.gov', 'https://picsum.photos/200/307', 351, '+963112345608', NULL),
           (nextval('gov_seq'), 'وزارة الصحة', 'health@government.gov', 'https://picsum.photos/200/308', 401, '+963112345609', NULL),
           (nextval('gov_seq'), 'وزارة الشؤون الاجتماعية والعمل', 'social@government.gov', 'https://picsum.photos/200/309', 451, '+963112345610', NULL),
           (nextval('gov_seq'), 'وزارة الدفاع', 'defense@government.gov', 'https://picsum.photos/200/310', 501, '+963112345611', NULL),
           (nextval('gov_seq'), 'وزارة الإدارة المحلية والبيئة', 'localadmin@government.gov', 'https://picsum.photos/200/311', 551, '+963112345612', NULL),
           (nextval('gov_seq'), 'وزارة الاتصالات وتقانة المعلومات', 'communications@government.gov', 'https://picsum.photos/200/312', 601, '+963112345613', NULL),
           (nextval('gov_seq'), 'وزارة النقل', 'transport@government.gov', 'https://picsum.photos/200/313', 651, '+963112345614', NULL),
           (nextval('gov_seq'), 'وزارة الزراعة', 'agriculture@government.gov', 'https://picsum.photos/200/314', 701, '+963112345615', NULL),
           (nextval('gov_seq'), 'وزارة الأشغال العامة والإسكان', 'publicworks@government.gov', 'https://picsum.photos/200/315', 751, '+963112345616', NULL),
           (nextval('gov_seq'), 'وزارة الثقافة', 'culture@government.gov', 'https://picsum.photos/200/316', 801, '+963112345617', NULL),
           (nextval('gov_seq'), 'وزارة السياحة', 'tourism@government.gov', 'https://picsum.photos/200/317', 851, '+963112345618', NULL),
           (nextval('gov_seq'), 'وزارة الرياضة والشباب', 'sports@government.gov', 'https://picsum.photos/200/318', 901, '+963112345619', NULL),
           (nextval('gov_seq'), 'وزارة التنمية الإدارية', 'admindevelopment@government.gov', 'https://picsum.photos/200/319', 951, '+963112345620', NULL),
           (nextval('gov_seq'), 'وزارة الطوارئ والكوارث', 'emergency@government.gov', 'https://picsum.photos/200/320', 1001, '+963112345621', NULL);


    INSERT INTO Gov (id, name, email, logo_url, address_id, phone, parent_gov_id) VALUES
            -- وزارة الطاقة (parent 1)
            (nextval('gov_seq'), 'هيئة الكهرباء', 'electricity@energy.gov', 'https://picsum.photos/200/321', 1051, '+963112345701', 1),
            (nextval('gov_seq'), 'هيئة النفط', 'oil@energy.gov', 'https://picsum.photos/200/322', 1101, '+963112345702', 1),
            (nextval('gov_seq'), 'هيئة الطاقة المتجددة', 'renewable@energy.gov', 'https://picsum.photos/200/323', 1151, '+963112345703', 1),

            -- وزارة العدل (parent 51)
            (nextval('gov_seq'), 'المحكمة الدستورية العليا', 'constitutionalcourt@justice.gov', 'https://picsum.photos/200/324', 1201, '+963112345704', 51),
            (nextval('gov_seq'), 'هيئة القضاء', 'judiciary@justice.gov', 'https://picsum.photos/200/325', 1251, '+963112345705', 51),
            (nextval('gov_seq'), 'هيئة النيابة العامة', 'prosecution@justice.gov', 'https://picsum.photos/200/326', 1301, '+963112345706', 51),

            -- وزارة الداخلية (parent 101)
            (nextval('gov_seq'), 'مديرية الأمن العام', 'security@interior.gov', 'https://picsum.photos/200/327', 1351, '+963112345707', 101),
            (nextval('gov_seq'), 'مديرية المرور', 'traffic@interior.gov', 'https://picsum.photos/200/328', 1401, '+963112345708', 101),
            (nextval('gov_seq'), 'مديرية الجوازات', 'passports@interior.gov', 'https://picsum.photos/200/329', 1451, '+963112345709', 101),

            -- وزارة المالية (parent 151)
            (nextval('gov_seq'), 'هيئة الضرائب', 'taxes@finance.gov', 'https://picsum.photos/200/330', 1501, '+963112345710', 151),
            (nextval('gov_seq'), 'هيئة الجمارك', 'customs@finance.gov', 'https://picsum.photos/200/331', 1551, '+963112345711', 151),
            (nextval('gov_seq'), 'البنك المركزي', 'centralbank@finance.gov', 'https://picsum.photos/200/332', 1601, '+963112345712', 151),

            -- وزارة الاقتصاد والصناعة (parent 201)
            (nextval('gov_seq'), 'هيئة الاستثمار', 'investment@economy.gov', 'https://picsum.photos/200/333', 1651, '+963112345713', 201),
            (nextval('gov_seq'), 'هيئة المواصفات والمقاييس', 'standards@economy.gov', 'https://picsum.photos/200/334', 1701, '+963112345714', 201),
            (nextval('gov_seq'), 'غرفة الصناعة', 'industry@economy.gov', 'https://picsum.photos/200/335', 1751, '+963112345715', 201),

            -- وزارة الأوقاف (parent 251)
            (nextval('gov_seq'), 'هيئة المساجد', 'mosques@endowments.gov', 'https://picsum.photos/200/336', 1801, '+963112345716', 251),
            (nextval('gov_seq'), 'هيئة الأوقاف الإسلامية', 'islamicendowments@endowments.gov', 'https://picsum.photos/200/337', 1851, '+963112345717', 251),
            (nextval('gov_seq'), 'هيئة الدعوة الدينية', 'religiouspreaching@endowments.gov', 'https://picsum.photos/200/338', 1901, '+963112345718',251),

            -- وزارة التربية (parent 301)
            (nextval('gov_seq'), 'مديرية التعليم الأساسي', 'basiceducation@education.gov', 'https://picsum.photos/200/339', 1951, '+963112345719', 301),
            (nextval('gov_seq'), 'مديرية التعليم الثانوي', 'secondaryeducation@education.gov', 'https://picsum.photos/200/340', 2001, '+963112345720', 301),
            (nextval('gov_seq'), 'هيئة الامتحانات', 'examinations@education.gov', 'https://picsum.photos/200/341', 2051, '+963112345721', 301),

            -- وزارة التعليم العالي (parent 351)
            (nextval('gov_seq'), 'هيئة الاعتماد والجودة', 'accreditation@highereducation.gov', 'https://picsum.photos/200/342', 2101, '+963112345722', 351),
            (nextval('gov_seq'), 'هيئة البحث العلمي', 'research@highereducation.gov', 'https://picsum.photos/200/343', 2151, '+963112345723', 351),
            (nextval('gov_seq'), 'المجلس الأعلى للجامعات', 'universities@highereducation.gov', 'https://picsum.photos/200/344', 2201, '+963112345724', 351),

            -- وزارة الصحة (parent 401)
            (nextval('gov_seq'), 'هيئة المستشفيات', 'hospitals@health.gov', 'https://picsum.photos/200/345', 2251, '+963112345725', 401),
            (nextval('gov_seq'), 'هيئة الرعاية الصحية الأولية', 'primarycare@health.gov', 'https://picsum.photos/200/346', 2301, '+963112345726', 401),
            (nextval('gov_seq'), 'هيئة الغذاء والدواء', 'fda@health.gov', 'https://picsum.photos/200/347', 2351, '+963112345727', 401),

            -- وزارة الشؤون الاجتماعية والعمل (parent 451)
            (nextval('gov_seq'), 'هيئة الضمان الاجتماعي', 'socialsecurity@social.gov', 'https://picsum.photos/200/348', 2401, '+963112345728', 451),
            (nextval('gov_seq'), 'هيئة العمل والتشغيل', 'employment@social.gov', 'https://picsum.photos/200/349', 2451, '+963112345729', 451),
            (nextval('gov_seq'), 'هيئة الرعاية الاجتماعية', 'welfare@social.gov', 'https://picsum.photos/200/350', 2501, '+963112345730', 451),

            -- وزارة الدفاع (parent 501)
            (nextval('gov_seq'), 'القوات البرية', 'army@defense.gov', 'https://picsum.photos/200/351', 2551, '+963112345731', 501),
            (nextval('gov_seq'), 'القوات الجوية', 'airforce@defense.gov', 'https://picsum.photos/200/352', 2601, '+963112345732', 501),
            (nextval('gov_seq'), 'القوات البحرية', 'navy@defense.gov', 'https://picsum.photos/200/353', 2651, '+963112345733', 501),

            -- وزارة الإدارة المحلية والبيئة (parent 551)
            (nextval('gov_seq'), 'هيئة البلديات', 'municipalities@localadmin.gov', 'https://picsum.photos/200/354', 2701, '+963112345734', 551),
            (nextval('gov_seq'), 'هيئة حماية البيئة', 'environment@localadmin.gov', 'https://picsum.photos/200/355', 2751, '+963112345735', 551),
            (nextval('gov_seq'), 'هيئة التخطيط العمراني', 'urbanplanning@localadmin.gov', 'https://picsum.photos/200/356', 2801, '+963112345736', 551),

            -- وزارة الاتصالات وتقانة المعلومات (parent 601)
            (nextval('gov_seq'), 'هيئة الاتصالات', 'telecom@communications.gov', 'https://picsum.photos/200/357', 2851, '+963112345737', 601),
            (nextval('gov_seq'), 'هيئة البريد', 'post@communications.gov', 'https://picsum.photos/200/358', 2901, '+963112345738', 601),
            (nextval('gov_seq'), 'هيئة المعلوماتية', 'informatics@communications.gov', 'https://picsum.photos/200/359', 2951, '+963112345739', 601),

            -- وزارة النقل (parent 651)
            (nextval('gov_seq'), 'هيئة الطرق', 'roads@transport.gov', 'https://picsum.photos/200/360', 3001, '+963112345740', 651),
            (nextval('gov_seq'), 'هيئة النقل البري', 'landtransport@transport.gov', 'https://picsum.photos/200/361', 3051, '+963112345741', 651),
            (nextval('gov_seq'), 'هيئة النقل الجوي', 'airtransport@transport.gov', 'https://picsum.photos/200/362', 3101, '+963112345742', 651),

            -- وزارة الزراعة (parent 701)
            (nextval('gov_seq'), 'هيئة الري', 'irrigation@agriculture.gov', 'https://picsum.photos/200/363', 3151, '+963112345743', 701),
            (nextval('gov_seq'), 'هيئة الثروة الحيوانية', 'livestock@agriculture.gov', 'https://picsum.photos/200/364', 3201, '+963112345744', 701);



    -- Populate Users (50 records)
    INSERT INTO Users (id, keycloak_id, first_name, last_name, email, phone, date_of_birth,
                       college_degree, job, cv_url, photo_url, description, address_id, gov_id, deleted)
    SELECT
        nextval('users_seq'),
        'kcid-' || i,
        CASE WHEN i % 3 = 0 THEN 'علي' WHEN i % 3 = 1 THEN 'فاطمة' ELSE 'حسن' END,
        CASE WHEN i % 4 = 0 THEN 'الزبيدي' WHEN i % 4 = 1 THEN 'السيد' WHEN i % 4 = 2 THEN 'الكعبي' ELSE 'العمري' END,
        'user' || i || '@email.com',
        '+1' || (4000000000 + (i * 22222))::TEXT,
        date '1990-01-01' + (i || ' days')::interval,
        CASE WHEN i % 2 = 0 THEN 'بكالوريوس علوم الحاسوب' ELSE 'ماجستير إدارة أعمال' END,
        (ARRAY['مهندس','مدير','محلل','مطور'])[(i % 4) + 1],
        'http://cv.example.com/' || i,
        'http://photos.example.com/' || i || '.jpg',
        'وصف المستخدم للرقم ' || i,
        a.id,
        g.id,
        null
    FROM generate_series(1, 50) i
    JOIN Address a ON a.id = 1 + ((i-1) * 50)
    JOIN Gov g ON g.id = 1 + ((i-1) * 50);

    -- Populate Problem_Category (50 records)
    INSERT INTO Problem_Category (id, name, gov_id) VALUES
    -- وزارة الطاقة (id=1)
    (nextval('problem_category_seq'), 'غير ذلك', 1),
    (nextval('problem_category_seq'), 'وقود', 1),
    (nextval('problem_category_seq'), 'طاقة متجددة', 1),

    -- وزارة العدل (id=51)
    (nextval('problem_category_seq'), 'قضايا جنائية', 51),
    (nextval('problem_category_seq'), 'قضايا مدنية', 51),
    (nextval('problem_category_seq'), 'أحوال شخصية', 51),

    -- وزارة الداخلية (id=101)
    (nextval('problem_category_seq'), 'أمن عام', 101),
    (nextval('problem_category_seq'), 'مرور', 101),
    (nextval('problem_category_seq'), 'جوازات', 101),

    -- وزارة المالية (id=151)
    (nextval('problem_category_seq'), 'ضرائب', 151),
    (nextval('problem_category_seq'), 'جمارك', 151),

    -- وزارة الاقتصاد والصناعة (id=201)
    (nextval('problem_category_seq'), 'استثمارات', 201),
    (nextval('problem_category_seq'), 'مراقبة جودة', 201),

    -- وزارة الأوقاف (id=251)
    (nextval('problem_category_seq'), 'مساجد', 251),
    (nextval('problem_category_seq'), 'أوقاف إسلامية', 251),

    -- وزارة التربية (id=301)
    (nextval('problem_category_seq'), 'مناهج تعليمية', 301),
    (nextval('problem_category_seq'), 'امتحانات', 301),

    -- وزارة التعليم العالي (id=351)
    (nextval('problem_category_seq'), 'اعتماد جامعات', 351),
    (nextval('problem_category_seq'), 'منح دراسية', 351),

    -- وزارة الصحة (id=401)
    (nextval('problem_category_seq'), 'مستشفيات', 401),
    (nextval('problem_category_seq'), 'أدوية', 401),
    (nextval('problem_category_seq'), 'رعاية أولية', 401),

    -- وزارة الشؤون الاجتماعية والعمل (id=451)
    (nextval('problem_category_seq'), 'ضمان اجتماعي', 451),
    (nextval('problem_category_seq'), 'بطالة', 451),

    -- وزارة الدفاع (id=501)
    (nextval('problem_category_seq'), 'تجنيد', 501),
    (nextval('problem_category_seq'), 'معدات عسكرية', 501),

    -- وزارة الإدارة المحلية والبيئة (id=551)
    (nextval('problem_category_seq'), 'بلديات', 551),
    (nextval('problem_category_seq'), 'نفايات', 551),

    -- وزارة الاتصالات وتقانة المعلومات (id=601)
    (nextval('problem_category_seq'), 'إنترنت', 601),
    (nextval('problem_category_seq'), 'بريد', 601),

    -- وزارة النقل (id=651)
    (nextval('problem_category_seq'), 'طرق', 651),
    (nextval('problem_category_seq'), 'تراخيص مركبات', 651),

    -- وزارة الزراعة (id=701)
    (nextval('problem_category_seq'), 'ري', 701),
    (nextval('problem_category_seq'), 'محاصيل', 701),

    -- وزارة الأشغال العامة والإسكان (id=751)
    (nextval('problem_category_seq'), 'إسكان', 751),
    (nextval('problem_category_seq'), 'بنية تحتية', 751),

    -- وزارة الثقافة (id=801)
    (nextval('problem_category_seq'), 'تراث', 801),
    (nextval('problem_category_seq'), 'فنون', 801),

    -- وزارة السياحة (id=851)
    (nextval('problem_category_seq'), 'آثار', 851),
    (nextval('problem_category_seq'), 'فنادق', 851),

    -- وزارة الرياضة والشباب (id=901)
    (nextval('problem_category_seq'), 'ملاعب', 901),
    (nextval('problem_category_seq'), 'نوادي شبابية', 901),

    -- وزارة التنمية الإدارية (id=951)
    (nextval('problem_category_seq'), 'تدريب موظفين', 951),
    (nextval('problem_category_seq'), 'إصلاح إداري', 951),

    -- وزارة الطوارئ والكوارث (id=1001)
    (nextval('problem_category_seq'), 'كوارث طبيعية', 1001),
    (nextval('problem_category_seq'), 'إغاثة', 1001);


    INSERT INTO Problem_Category (id, name, gov_id) VALUES
    (nextval('problem_category_seq'), 'غير ذلك', 1),
    (nextval('problem_category_seq'), 'غير ذلك', 51),
    (nextval('problem_category_seq'), 'غير ذلك', 101),
    (nextval('problem_category_seq'), 'غير ذلك', 151),
    (nextval('problem_category_seq'), 'غير ذلك', 201),
    (nextval('problem_category_seq'), 'غير ذلك', 251),
    (nextval('problem_category_seq'), 'غير ذلك', 301),
    (nextval('problem_category_seq'), 'غير ذلك', 351),
    (nextval('problem_category_seq'), 'غير ذلك', 401),
    (nextval('problem_category_seq'), 'غير ذلك', 451),
    (nextval('problem_category_seq'), 'غير ذلك', 501),
    (nextval('problem_category_seq'), 'غير ذلك', 551),
    (nextval('problem_category_seq'), 'غير ذلك', 601),
    (nextval('problem_category_seq'), 'غير ذلك', 651),
    (nextval('problem_category_seq'), 'غير ذلك', 701),
    (nextval('problem_category_seq'), 'غير ذلك', 751),
    (nextval('problem_category_seq'), 'غير ذلك', 801),
    (nextval('problem_category_seq'), 'غير ذلك', 851),
    (nextval('problem_category_seq'), 'غير ذلك', 901),
    (nextval('problem_category_seq'), 'غير ذلك', 951),
    (nextval('problem_category_seq'), 'غير ذلك', 1001),
    (nextval('problem_category_seq'), 'غير ذلك', 1051),
    (nextval('problem_category_seq'), 'غير ذلك', 1101),
    (nextval('problem_category_seq'), 'غير ذلك', 1151),
    (nextval('problem_category_seq'), 'غير ذلك', 1201),
    (nextval('problem_category_seq'), 'غير ذلك', 1251),
    (nextval('problem_category_seq'), 'غير ذلك', 1301),
    (nextval('problem_category_seq'), 'غير ذلك', 1351),
    (nextval('problem_category_seq'), 'غير ذلك', 1401),
    (nextval('problem_category_seq'), 'غير ذلك', 1451),
    (nextval('problem_category_seq'), 'غير ذلك', 1501),
    (nextval('problem_category_seq'), 'غير ذلك', 1551),
    (nextval('problem_category_seq'), 'غير ذلك', 1601),
    (nextval('problem_category_seq'), 'غير ذلك', 1651),
    (nextval('problem_category_seq'), 'غير ذلك', 1701),
    (nextval('problem_category_seq'), 'غير ذلك', 1751),
    (nextval('problem_category_seq'), 'غير ذلك', 1801),
    (nextval('problem_category_seq'), 'غير ذلك', 1851),
    (nextval('problem_category_seq'), 'غير ذلك', 1901),
    (nextval('problem_category_seq'), 'غير ذلك', 1951),
    (nextval('problem_category_seq'), 'غير ذلك', 2001),
    (nextval('problem_category_seq'), 'غير ذلك', 2051),
    (nextval('problem_category_seq'), 'غير ذلك', 2101),
    (nextval('problem_category_seq'), 'غير ذلك', 2151),
    (nextval('problem_category_seq'), 'غير ذلك', 2201),
    (nextval('problem_category_seq'), 'غير ذلك', 2251);

    -- Populate Problem (50 records)
    INSERT INTO Problem (id, title, description, is_real, for_contribution, for_donation,
                        submission_date, submitted_by_user_id, approved_by_user_id,
                        rejection_reason, address_id, category_id, status)
    SELECT
        nextval('problem_seq'),
        'المشكلة رقم ' || i || ': ' || (ARRAY['حفرة','اضاءة مكسورة','مستلزمات مدرسية','شارع متسخ','تسريب مياه'])[(i % 5) + 1],
        'تفاصيل المشكلة رقم ' || i,
        i % 3 != 0,
        i % 2 = 0,
        i % 4 = 0,
        now() - (i || ' hours')::interval,
        u.id,
        CASE WHEN i % 5 = 1 THEN (SELECT id FROM Users ORDER BY random() LIMIT 1) ELSE NULL END, -- APPROVED is at index 2 (i%5=1)
        CASE WHEN i % 5 = 2 THEN 'Duplicate entry' ELSE NULL END, -- REJECTED is at index 3 (i%5=2)
        a.id,
        pc.id,
        (ARRAY['PENDING_APPROVAL','APPROVED','REJECTED','IN_PROGRESS','RESOLVED'])[(i % 5) + 1]
    FROM generate_series(1, 50) i
    JOIN Address a ON a.id = 1 + ((i-1) * 50)
    JOIN Problem_Category pc ON pc.id = 1 + ((i-1) * 50)
    JOIN Users u ON u.id = 1 + ((i-1) * 50);

    -- Populate Problem_Photo (50 records)
    INSERT INTO Problem_Photo (id, problem_id, s3_Key, photo_date)
    SELECT
        nextval('problem_photo_seq'),
        p.id,
        'problem/' || i ,
        now() - (i || ' hours')::interval
    FROM generate_series(1, 50) i
    JOIN Problem p ON p.id = 1 + ((i-1) * 50);

    -- Populate Solution (50 records)
    INSERT INTO Solution (id, description, estimated_cost, status, accepted_reason,
                         start_date, end_date, feedback, rating, problem_id,
                         proposed_by_user_id, accepted_by_user_id)
    SELECT
        nextval('solution_seq'),
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
    JOIN Problem p ON p.id = 1 + ((i-1) * 50)
    JOIN Users u ON u.id = 1 + ((i-1) * 50);

    -- Populate Donation (50 records)
    INSERT INTO Donation (id, problem_id, donor_id, amount, currency, payment_method,
                         payment_transaction_id, status, is_anonymous, donation_date)
    SELECT
        nextval('donation_seq'),
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
    JOIN Problem p ON p.id = 1 + ((i-1) * 50)
    JOIN Users u ON u.id = 1 + ((i-1) % 50 * 50);

    -- Populate Problem_Progress (50 records)
    INSERT INTO Problem_Progress (id, percentage, comment, progress_date, problem_id, solution_id)
    SELECT
        nextval('problem_progress_seq'),
        (i * 2) % 100,
        'التقدم رقم ' || i,
        now() - (i || ' hours')::interval,
        p.id,
        s.id
    FROM generate_series(1, 50) i
    JOIN Problem p ON p.id = 1 + ((i-1) * 50)
    JOIN Solution s ON s.id = 1 + ((i-1) * 50);
END;
$$ LANGUAGE plpgsql;

-- Execute the function
SELECT populate_all_tables();