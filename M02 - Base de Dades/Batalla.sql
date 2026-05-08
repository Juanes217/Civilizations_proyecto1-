-- ==========================================================
-- 1. PRIMERO: BATTLE_STATS (La tabla MADRE)
-- ==========================================================
CREATE TABLE battle_stats (
    civilization_id NUMBER(10) NOT NULL,
    num_battle NUMBER(10) NOT NULL,
    wood_acquired NUMBER(10),
    iron_acquired NUMBER(10),
    result VARCHAR2(10), 
    CONSTRAINT pk_battle_stats PRIMARY KEY (civilization_id, num_battle),
    -- Esta FK necesita que CIVILIZATION_STATS exista (de tu otro script)
    CONSTRAINT fk_battle_stats_civilization FOREIGN KEY (civilization_id) REFERENCES civilization_stats (civilization_id)
);

-- ==========================================================
-- 2. SEGUNDO: Tablas HIJAS (Estadísticas de la civilización)
-- ==========================================================
CREATE TABLE civilization_attack_stats (
    civilization_id NUMBER(10) NOT NULL,
    num_battle NUMBER(10) NOT NULL,
    type_stats VARCHAR2(50) NOT NULL,
    initial_stats NUMBER(10),
    drops NUMBER(10),
    CONSTRAINT pk_civ_attack_stats PRIMARY KEY (civilization_id, num_battle, type_stats),
    CONSTRAINT fk_civ_att_battle FOREIGN KEY (civilization_id, num_battle) REFERENCES battle_stats (civilization_id, num_battle)
);

CREATE TABLE civilization_defense_stats (
    civilization_id NUMBER(10) NOT NULL,
    num_battle NUMBER(10) NOT NULL,
    type_stats VARCHAR2(50) NOT NULL,
    initial_stats NUMBER(10),
    drops NUMBER(10),
    CONSTRAINT pk_civ_def_stats PRIMARY KEY (civilization_id, num_battle, type_stats),
    CONSTRAINT fk_civ_def_battle FOREIGN KEY (civilization_id, num_battle) REFERENCES battle_stats (civilization_id, num_battle)
);

CREATE TABLE civilization_special_stats (
    civilization_id NUMBER(10) NOT NULL,
    num_battle NUMBER(10) NOT NULL,
    type_stats VARCHAR2(50) NOT NULL,
    initial_stats NUMBER(10),
    drops NUMBER(10),
    CONSTRAINT pk_civ_spe_stats PRIMARY KEY (civilization_id, num_battle, type_stats),
    CONSTRAINT fk_civ_spe_battle FOREIGN KEY (civilization_id, num_battle) REFERENCES battle_stats (civilization_id, num_battle)
);

-- ==========================================================
-- 3. TERCERO: Estadísticas del Enemigo
-- ==========================================================
CREATE TABLE enemy_attack_stats (
    civilization_id NUMBER(10) NOT NULL,
    num_battle NUMBER(10) NOT NULL,
    type_stats VARCHAR2(50) NOT NULL,
    initial_stats NUMBER(10),
    drops NUMBER(10),
    CONSTRAINT pk_enemy_attack_stats PRIMARY KEY (civilization_id, num_battle, type_stats),
    CONSTRAINT fk_enemy_att_battle FOREIGN KEY (civilization_id, num_battle) REFERENCES battle_stats (civilization_id, num_battle)
);

-- ==========================================================
-- 4. CUARTO: Log de batalla
-- ==========================================================
CREATE TABLE battle_log (
    civilization_id NUMBER(10) NOT NULL,
    num_battle NUMBER(10) NOT NULL,
    num_line NUMBER(10) NOT NULL,
    log_entry VARCHAR2(500), 
    CONSTRAINT pk_battle_log PRIMARY KEY (civilization_id, num_battle, num_line),
    CONSTRAINT fk_log_battle FOREIGN KEY (civilization_id, num_battle) REFERENCES battle_stats (civilization_id, num_battle)
);