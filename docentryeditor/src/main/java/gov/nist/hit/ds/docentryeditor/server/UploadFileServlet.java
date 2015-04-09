package gov.nist.hit.ds.docentryeditor.server;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.logging.Logger;

public class UploadFileServlet extends HttpServlet {
	private final static Logger LOGGER = Logger.getLogger(UploadFileServlet.class.getName());
	private static final long serialVersionUID = 1L;
	private final static Charset ENCODING = StandardCharsets.UTF_8;
	private final static String FILE_REPOSITORY = "files";


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
					LOGGER.fine("file uploaded on server side");

					// Reading file content
					InputStream is = item.openStream();
					byte[] encoded = IOUtils.toByteArray(is);
					String fileContent = ENCODING.decode(
							ByteBuffer.wrap(encoded)).toString();

					// Return file content to the client
//					resp.getOutputStream().print(filename+";^;^;"+fileContent);
                    LOGGER.info(fileContent);
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
		LOGGER.info("Temporary metadata xml file creation...");

		FileOutputStream out = new FileOutputStream(new File(FILE_REPOSITORY,
				filename));
		InputStream is = item.openStream();
		out.write(IOUtils.toByteArray(is));
		out.close();

		LOGGER.fine("... temporary file created: " + FILE_REPOSITORY + "/"
                + filename);

		return filename;
	}
}
