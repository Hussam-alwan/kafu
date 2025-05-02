-- Function to populate all tables
CREATE OR REPLACE FUNCTION populate_all_tables()
RETURNS void AS $$
DECLARE
    city_names TEXT[] := ARRAY['DAMASCUS', 'ALEPPO', 'HOMS', 'HAMAH', 'IDLIB',
                              'QUNAITRA', 'DARAA', 'SUWAIDA', 'TARTUS', 'LATAKIA'];
BEGIN
    -- Populate Address (50 records)
    INSERT INTO Address (id, latitude, longitude, description, city)
    SELECT
        nextval('address_seq'),
        (random() * 5 - 2.5),
        (random() * 5 - 2.5),
        'Location ' || i || ' description',
        city_names[(i % 10) + 1]
    FROM generate_series(1, 50) i;

    -- Populate Gov (50 records)
    INSERT INTO Gov (id, name, email, logo_url, address_id, phone, parent_gov_id)
    SELECT
        nextval('gov_seq'),
        'Gov Agency ' || i,
        'gov' || i || '@agency.com',
        'https://logo.gov/' || i || '.png',
        a.id,
        '+1' || (3000000000 + (i * 11111))::TEXT,
        CASE WHEN i % 5 = 0 THEN NULL ELSE (SELECT id FROM Gov ORDER BY random() LIMIT 1) END
    FROM generate_series(1, 50) i
    JOIN Address a ON a.id = 1 + ((i-1) * 50);

    -- Populate Users (50 records)
    INSERT INTO Users (id, keycloak_id, first_name, last_name, email, phone, date_of_birth,
                      college_degree, job, cv_url, photo_url, description, address_id, gov_id)
    SELECT
        nextval('users_seq'),
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
        g.id
    FROM generate_series(1, 50) i
    JOIN Address a ON a.id = 1 + ((i-1) * 50)
    JOIN Gov g ON g.id = 1 + ((i-1) * 50);

    -- Populate Problem_Category (50 records)
    INSERT INTO Problem_Category (category_id, name, gov_id)
    SELECT
        nextval('problem_category_seq'),
        'Category ' || (i % 5 + 1) || '-' || i,
        g.id
    FROM generate_series(1, 50) i
    JOIN Gov g ON g.id = 1 + ((i-1) * 50);

    -- Populate Problem (50 records)
    INSERT INTO Problem (id, title, description, is_real, for_contribution, for_donation,
                        submission_date, submitted_by_user_id, approved_by_user_id,
                        rejection_reason, address_id, category_id, status)
    SELECT
        nextval('problem_seq'),
        'Problem ' || i || ': ' || (ARRAY['Pothole','Light Out','Graffiti','Leak','Debris'])[(i % 5) + 1],
        'Detailed description of issue ' || i,
        i % 3 != 0,
        i % 2 = 0,
        i % 4 = 0,
        now() - (i || ' hours')::interval,
        u.id,
        CASE WHEN i % 4 = 0 THEN NULL ELSE (SELECT id FROM Users ORDER BY random() LIMIT 1) END,
        CASE WHEN i % 10 = 0 THEN 'Duplicate entry' ELSE NULL END,
        a.id,
        pc.category_id,
        (ARRAY['PENDING_APPROVAL','APPROVED','REJECTED','IN_PROGRESS','RESOLVED'])[(i % 5) + 1]
    FROM generate_series(1, 50) i
    JOIN Address a ON a.id = 1 + ((i-1) * 50)
    JOIN Problem_Category pc ON pc.category_id = 1 + ((i-1) * 50)
    JOIN Users u ON u.id = 1 + ((i-1) * 50);

    -- Populate Problem_Photo (50 records)
    INSERT INTO Problem_Photo (id, problem_id, photo_url, photo_date)
    SELECT
        nextval('problem_photo_seq'),
        p.id,
        'https://photos.example.com/problem/' || i || '.jpg',
        now() - (i || ' hours')::interval
    FROM generate_series(1, 50) i
    JOIN Problem p ON p.id = 1 + ((i-1) * 50);

    -- Populate Solution (50 records)
    INSERT INTO Solution (id, description, estimated_cost, status, accepted_reason,
                         start_date, end_date, feedback, rating, problem_id,
                         proposed_by_user_id, accepted_by_user_id)
    SELECT
        nextval('solution_seq'),
        'Solution proposal for issue ' || i,
        (1000 + (random() * 99000))::numeric(12,2),
        (ARRAY['PENDING_APPROVAL','ACCEPTED','REJECTED','WORKINPROGRESS'])[(i % 4) + 1],
        CASE WHEN i % 4 = 1 THEN 'Cost effective' ELSE NULL END,
        current_date + (i || ' days')::interval,
        current_date + ((i + 7) || ' days')::interval,
        CASE WHEN i % 3 = 0 THEN 'Good progress' ELSE NULL END,
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
        (ARRAY['STRIPE','PAYPAL'])[(i % 2) + 1],
        'txn_' || md5(random()::text),
        (ARRAY['PENDING','SUCCEEDED','FAILED'])[(i % 3) + 1],
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
        'Progress update ' || i,
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