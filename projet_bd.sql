
CREATE TABLE Restaurant(email_resto varchar(50) PRIMARY KEY,
                        nom_resto varchar(50) NOT NULL,
                        tel varchar(20) NOT NULL,
                        adresse_resto varchar(100) NOT NULL,
                        horaires varchar(50) NOT NULL,
                        capacite int CHECK(capacite > 0) NOT NULL,
                        texte_resto varchar(200) NOT NULL,
                        note_resto float); /* toutes les evals sont comprises entre 0 et 5 donc la moyenne aussi */

CREATE TABLE Plat(id_plat int,
                    email_resto varchar(50),
                    nom_plat varchar(50) NOT NULL,
                    description_plat varchar(200) NOT NULL,
                    prix float CHECK(prix >= 0) NOT NULL,
                    PRIMARY KEY(id_plat, email_resto),
                    CONSTRAINT resto_plat FOREIGN KEY (email_resto) REFERENCES Restaurant(email_resto) ON DELETE CASCADE);

CREATE TABLE Commande(id_cmd int PRIMARY KEY,
                    date_cmd date NOT NULL,
                    heure varchar(10) NOT NULL,
                    type_cmd varchar(10) NOT NULL,
                    montant float CHECK(montant >= 0) NOT NULL,
                    statut varchar(30) NOT NULL,
                    CONSTRAINT type_cmd_commande FOREIGN KEY (type_cmd) REFERENCES TypeCmd(type_cmd),
                    CONSTRAINT statut_commande FOREIGN KEY (statut) REFERENCES Statut(statut));

CREATE TABLE Commande_livraison(id_cmd int PRIMARY KEY,
                                adresse_livraison varchar(100)  NOT NULL,
                                texte_livraison varchar(200),
                                heure_livraison varchar(10) NOT NULL,
                                CONSTRAINT cmd_livraison FOREIGN KEY (id_cmd) REFERENCES Commande(id_cmd) ON DELETE CASCADE);


CREATE TABLE Commande_sur_place(id_cmd int PRIMARY KEY,
                                nbr_clients int NOT NULL,
                                heure_arrivee varchar(10) NOT NULL,
                                CONSTRAINT cmd_sur_place FOREIGN KEY (id_cmd) REFERENCES Commande(id_cmd) ON DELETE CASCADE);

CREATE TABLE TypeCmd(type_cmd varchar(10) PRIMARY KEY CHECK (type_cmd IN ('sur_place', 'a_emporter', 'livraison')));

CREATE TABLE Statut(statut varchar(30) PRIMARY KEY CHECK (statut IN ('attente_de_confirmation',
'validee', 'disponible', 'en_livraison', 'annulee_par_le_client', 'annulee_par_le_restaurant', 'livree')));

CREATE TABLE Eval(id_cmd int PRIMARY KEY,
                    date_eval date NOT NULL,
                    heure_eval varchar(10) NOT NULL,
                    avis varchar(200),
                    note int CHECK(0 <= note AND note <= 5) NOT NULL,
                    CONSTRAINT cmd_eval FOREIGN KEY (id_cmd) REFERENCES Commande(id_cmd) ON DELETE CASCADE);

CREATE TABLE Categorie(categorie varchar(20) PRIMARY KEY);

CREATE TABLE Client(id_client int PRIMARY KEY,
                    email_client varchar(50),
                    mdp varchar(20),
                    nom_client varchar(20),
                    prenom_client varchar(20),
                    adresse_client varchar(100));

CREATE TABLE Allergenes(allergenes varchar(20) PRIMARY KEY CHECK (allergenes IN ('gluten',
'crustaces', 'oeufs', 'arachides', 'soja', 'lait', 'fruit_a_coques', 'celeri', 'moutarde',
'sesame', 'anhydride_sulfureux', 'lupin', 'mollusques')));

CREATE TABLE Contient(id_cmd int,
                    id_plat int,
                    email_resto varchar(50),
                    PRIMARY KEY(id_cmd, id_plat, email_resto),
                    CONSTRAINT cmd_contient FOREIGN KEY (id_cmd) REFERENCES Commande(id_cmd) ON DELETE CASCADE,
                    CONSTRAINT plat_contient FOREIGN KEY (id_plat, email_resto) REFERENCES Plat(id_plat, email_resto) ON DELETE CASCADE);

CREATE TABLE Propose_type(email_resto varchar(50),
                        type_cmd varchar(20),
                        PRIMARY KEY(email_resto, type_cmd),
                        CONSTRAINT resto_propose_type FOREIGN KEY (email_resto) REFERENCES Restaurant(email_resto) ON DELETE CASCADE,
                        CONSTRAINT type_cmd_propose_type FOREIGN KEY (type_cmd) REFERENCES TypeCmd(type_cmd) ON DELETE CASCADE);

CREATE TABLE Specialise_dans(email_resto varchar(50),
                            categorie varchar(20),
                            PRIMARY KEY(email_resto, categorie),
                            CONSTRAINT resto_specialise_dans FOREIGN KEY (email_resto) REFERENCES Restaurant(email_resto) ON DELETE CASCADE,
                            CONSTRAINT categorie_specialise_dans FOREIGN KEY (categorie) REFERENCES Categorie(categorie) ON DELETE CASCADE);

CREATE TABLE Parent_de(categorie_pere varchar(20),
                        categorie_fils varchar(20),
                        PRIMARY KEY(categorie_pere, categorie_fils),
                        CONSTRAINT pere_parent_de FOREIGN KEY (categorie_pere) REFERENCES Categorie(categorie) ON DELETE CASCADE,
                        CONSTRAINT fils_parent_de FOREIGN KEY (categorie_fils) REFERENCES Categorie(categorie) ON DELETE CASCADE);

CREATE TABLE P_a_allergenes(id_plat int,
                            email_resto varchar(50),
                            allergenes varchar(20),
                            PRIMARY KEY(id_plat, email_resto, allergenes),
                            CONSTRAINT plat_p_a_allergenes FOREIGN KEY (id_plat, email_resto) REFERENCES Plat(id_plat, email_resto) ON DELETE CASCADE,
                            CONSTRAINT allergenes_p_a_allergenes FOREIGN KEY (allergenes) REFERENCES Allergenes(allergenes) ON DELETE CASCADE);

CREATE TABLE Associer(id_client int,
                    id_cmd int,
                    email_resto varchar(50),
                    PRIMARY KEY (id_client, id_cmd, email_resto),
                    CONSTRAINT client_associer FOREIGN KEY (id_client) REFERENCES Client(id_client) ON DELETE CASCADE,
                    CONSTRAINT cmd_associer FOREIGN KEY (id_cmd) REFERENCES Commande(id_cmd) ON DELETE CASCADE,
                    CONSTRAINT resto_associer FOREIGN KEY (email_resto) REFERENCES Restaurant(email_resto) ON DELETE CASCADE);


INSERT INTO Restaurant VALUES ('diderot@grenoble-inp.org', 'diderot', '01010101', '12 rue sql', '12:00-14:00 / 19:00-21:00', 200, 'Pour le bonheur des etudiants', NULL);
INSERT INTO Restaurant VALUES ('intermezzo@grenoble-inp.org', 'intermezzo', '04040404', '13 rue java', '12:00-14:00 / 19:00-21:00', 300, 'Pour le grand bonheur des etudiants', NULL);
INSERT INTO Restaurant VALUES ('condillac@grenoble-inp.org', 'condillac', '01234567', '123 rue imag', '12:00-14:00 / 19:00-21:00', 200, 'Pour le petit bonheur des etudiants', NULL);
