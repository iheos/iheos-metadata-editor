package gov.nist.hit.ds.docentryeditor.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gov.nist.hit.ds.docentryeditor.shared.model.XdsDocumentEntry;
import gov.nist.hit.ds.ebMetadata.Metadata;
import gov.nist.hit.ds.ebMetadata.MetadataParser;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

public class UploadFileServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final String FILE_REPOSITORY = "files";
	private final Charset ENCODING = StandardCharsets.UTF_8;

	private final Logger logger = Logger.getLogger(UploadFileServlet.class
			.getName());

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException {
		try {
			ServletFileUpload upload = new ServletFileUpload();
			resp.setContentType("text/plain");
			FileItemIterator iterator = upload.getItemIterator(req);

			while (iterator.hasNext()) {
				FileItemStream item = iterator.next();
                String filename=item.getName();

				if (!item.isFormField()) {
					logger.fine("file uploaded on server side");

					// Reading file content
					InputStream is = item.openStream();
					byte[] encoded = IOUtils.toByteArray(is);
					String fileContent = ENCODING.decode(
							ByteBuffer.wrap(encoded)).toString();

					// Return file content to the client
//					resp.getOutputStream().print(filename+";^;^;"+fileContent);
                    logger.info(fileContent);
					resp.getOutputStream().print(fileContent);


                    // This was the previous method...
					// String filename=writeTemporaryFile(item);
					// // Send file's random name and its location
					// resp.getOutputStream().print(FILE_REPOSITORY + "/" +
					// filename);

				}
			}
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}

	/**
	 * A better solution without writing a temporary file has been implemented.
	 *
	 * @deprecated
	 * @param item
	 * @return filename
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private String writeTemporaryFile(FileItemStream item) throws IOException {
		// Random name created for save on server
		String filename = UUID.randomUUID().toString() + ".xml";

		// Recovery of submitted file and save it into "files"
		// repository
		logger.info("Temporary metadata xml file creation...");

		FileOutputStream out = new FileOutputStream(new File(FILE_REPOSITORY,
				filename));
		InputStream is = item.openStream();
		out.write(IOUtils.toByteArray(is));
		out.close();

		logger.fine("... temporary file created: " + FILE_REPOSITORY + "/"
				+ filename);

		return filename;
	}
}
