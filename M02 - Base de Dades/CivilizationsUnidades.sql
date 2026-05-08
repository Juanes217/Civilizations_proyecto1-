-- Borrar la secuencia si ya existe para evitar errores al re-ejecutar
-- DROP SEQUENCE civilization_seq; 

CREATE SEQUENCE civilization_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE civilization_stats (
    civilization_id NUMBER(10) NOT NULL,
    name VARCHAR2(50),
    wood_amount NUMBER(10),
    iron_amount NUMBER(10),
    food_amount NUMBER(10),
    mana_amount NUMBER(10),
    magictower_counter NUMBER(10) DEFAULT 0,
    church_counter NUMBER(10) DEFAULT 0,
    farm_counter NUMBER(10) DEFAULT 0,
    smithy_counter NUMBER(10) DEFAULT 0,
    carpentry_counter NUMBER(10) DEFAULT 0,
    technology_defense_level NUMBER(10) DEFAULT 0,
    technology_attack_level NUMBER(10) DEFAULT 0,
    battles_counter NUMBER(10) DEFAULT 0,
    CONSTRAINT pk_civilization_stats PRIMARY KEY (civilization_id)
);

CREATE TABLE ATTACK_UNITS_STATS (
    CIVILIZATION_ID NUMBER(10) NOT NULL,
    UNIT_ID NUMBER(10) NOT NULL,
    TYPE_UNIT VARCHAR2(20),
    ARMOR NUMBER(10),
    BASE_DAMAGE NUMBER(10),
    EXPERIENCE NUMBER(10) DEFAULT 0,
    SANCTIFIED VARCHAR2(3) DEFAULT 'NO',
    CONSTRAINT PK_ATTACK_UNIT PRIMARY KEY (CIVILIZATION_ID, UNIT_ID),
    CONSTRAINT FK_ATTACK_CIV_STATS FOREIGN KEY (CIVILIZATION_ID) REFERENCES CIVILIZATION_STATS (CIVILIZATION_ID),
    CONSTRAINT CHK_TYPE_ATTACK CHECK (TYPE_UNIT IN ('Swordsman', 'Spearman', 'Crossbow', 'Cannon'))
);

CREATE TABLE DEFENSE_UNITS_STATS (
    CIVILIZATION_ID NUMBER(10) NOT NULL,
    UNIT_ID NUMBER(10) NOT NULL,
    TYPE_UNIT VARCHAR2(30),
    ARMOR NUMBER(10),
    BASE_DAMAGE NUMBER(10),
    EXPERIENCE NUMBER(10) DEFAULT 0,
    SANCTIFIED VARCHAR2(3) DEFAULT 'NO',
    CONSTRAINT PK_DEFENSE_UNIT PRIMARY KEY (CIVILIZATION_ID, UNIT_ID),
    CONSTRAINT FK_DEFENSE_CIV_STATS FOREIGN KEY (CIVILIZATION_ID) REFERENCES CIVILIZATION_STATS (CIVILIZATION_ID),
    CONSTRAINT CHK_TYPE_DEFENSE CHECK (TYPE_UNIT IN ('Arrow Tower', 'Catapult', 'Rocket Launcher Tower'))
);

CREATE TABLE SPECIAL_UNITS_STATS (
    CIVILIZATION_ID NUMBER(10) NOT NULL,
    UNIT_ID NUMBER(10) NOT NULL,
    TYPE_UNIT VARCHAR2(20),
    ARMOR NUMBER(10),
    BASE_DAMAGE NUMBER(10),
    EXPERIENCE NUMBER(10) DEFAULT 0,
    CONSTRAINT PK_SPECIAL_UNIT PRIMARY KEY (CIVILIZATION_ID, UNIT_ID),
    CONSTRAINT FK_SPECIAL_CIV_STATS FOREIGN KEY (CIVILIZATION_ID) REFERENCES CIVILIZATION_STATS (CIVILIZATION_ID),
    CONSTRAINT CHK_TYPE_SPECIAL CHECK (TYPE_UNIT IN ('Magician', 'Priest'))
);






-- ==========================================
-- 1. INSERTAR LA CIVILIZACIÓN INICIAL
-- ==========================================
INSERT INTO civilization_stats (
    civilization_id, name, wood_amount, iron_amount, food_amount, mana_amount, 
    magictower_counter, church_counter, farm_counter, smithy_counter, carpentry_counter, 
    technology_defense_level, technology_attack_level, battles_counter
) VALUES (
    civilization_seq.NEXTVAL, 'Roma', 15000, 12000, 18000, 500, 
    1, 1, 2, 1, 1, 
    1, 1, 0
);

-- ==========================================
-- 2. INSERTAR EJÉRCITO DE ATAQUE INICIAL
-- ==========================================
-- Insertamos 2 Espadachines (ID 1 y 2)
INSERT INTO ATTACK_UNITS_STATS (CIVILIZATION_ID, UNIT_ID, TYPE_UNIT, ARMOR, BASE_DAMAGE, EXPERIENCE, SANCTIFIED)
VALUES (civilization_seq.CURRVAL, 1, 'Swordsman', 50, 25, 0, 'NO');
INSERT INTO ATTACK_UNITS_STATS (CIVILIZATION_ID, UNIT_ID, TYPE_UNIT, ARMOR, BASE_DAMAGE, EXPERIENCE, SANCTIFIED)
VALUES (civilization_seq.CURRVAL, 2, 'Swordsman', 50, 25, 0, 'NO');

-- Insertamos 1 Ballestero (ID 3)
INSERT INTO ATTACK_UNITS_STATS (CIVILIZATION_ID, UNIT_ID, TYPE_UNIT, ARMOR, BASE_DAMAGE, EXPERIENCE, SANCTIFIED)
VALUES (civilization_seq.CURRVAL, 3, 'Crossbow', 30, 45, 0, 'NO');

-- ==========================================
-- 3. INSERTAR DEFENSAS INICIALES
-- ==========================================
-- Insertamos 1 Torre de Flechas (ID 1)
INSERT INTO DEFENSE_UNITS_STATS (CIVILIZATION_ID, UNIT_ID, TYPE_UNIT, ARMOR, BASE_DAMAGE, EXPERIENCE, SANCTIFIED)
VALUES (civilization_seq.CURRVAL, 1, 'Arrow Tower', 100, 40, 0, 'NO');

-- ==========================================
-- 4. INSERTAR UNIDADES ESPECIALES
-- ==========================================
-- Insertamos 1 Mago (ID 1)
INSERT INTO SPECIAL_UNITS_STATS (CIVILIZATION_ID, UNIT_ID, TYPE_UNIT, ARMOR, BASE_DAMAGE, EXPERIENCE)
VALUES (civilization_seq.CURRVAL, 1, 'Magician', 20, 60, 0);

COMMIT;