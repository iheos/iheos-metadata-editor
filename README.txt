Things to know about the current state of the editor.

    - module docentryeditor-integ and docentryeditor-standalone were created for integration with toolkit v3,
    so the project could be changed back to a single module maven project.
    - maven pom.xml could use a clean up (especially change the version of toolkit used for parser and access
    to the external cache, which is link to a snapshot version as of now (that's in docentryeditor module))
    - the code for the file browser (for saving and opening file from the ext. cache) isn't finished yet.
    Most of that code is located in package gov.nist.hit.ds.docentryeditor.client.widgets.browser; but it really is
    just a start since I had to start it over only a week before I left.
    Here is the doc I was using to do it: http://www.gwtproject.org/doc/latest/DevGuideUiCellWidgets.html#cellbrowser
    - the code allowing to come in to the editor from the toolkit though a Place with parameters that we talked about
    hasn't been done at all.
