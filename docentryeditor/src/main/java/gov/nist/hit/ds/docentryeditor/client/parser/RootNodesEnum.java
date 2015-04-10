package gov.nist.hit.ds.docentryeditor.client.parser;

enum RootNodesEnum {
    titles, comments, authors, mimetype, hash, id, classcode, confidentialitycode, creationtime, eventcode, formatcode, healthcarefacilitytype, languagecode, legalauthenticator, patientid, practicesettingcode, repositoryuniqueid, servicestarttime, servicestoptime, size, sourcepatientid, sourcepatientinfo, typecode, uniqueid, uri;

    enum SubNodesEnum {
        authorperson, authorinstitution, authorrole, authorspecialty, authortelecommunication, value, idtype, language, information, codedterm, displayname, code, codingscheme, name;
    }
}
