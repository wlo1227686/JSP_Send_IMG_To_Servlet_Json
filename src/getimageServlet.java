
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@WebServlet("/getimageServlet")
public class getimageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public getimageServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		do_First(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		do_First(request, response);
	}

	public void do_First(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		BufferedReader br = request.getReader();
		StringBuffer jsonIn = new StringBuffer();
		String line = "";
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		Gson gson = new Gson();
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		String img = jsonObject.get("img").getAsString();
		System.out.println(img);
		System.out.println("size=" + jsonIn.length());
		byte[] image = Base64.getMimeDecoder().decode(img.split(",")[1]);
		System.out.println("IMGlength=" + image.length);
		//JDBC 
//		int ans = new IMGDAO().insert(new ByteArrayInputStream(image), image.length);
//		System.out.println("ans=" + ans);
		write_ByteArray_To_ByteFile(image);

		return;

	}

	public void write_ByteArray_To_ByteFile(byte[] byteArray) {

		File dir = new File("D:getData\\");
		if (!dir.exists())
			dir.mkdirs();
		try (FileOutputStream fos = new FileOutputStream(new File(dir, "mypicture.jpg"));
				ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);) {

			byte[] b = new byte[8192];
			int len = 0;
			while ((len = bais.read(b)) != -1) {
				fos.write(b, 0, len);
			}
			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("[位元陣列轉二進位檔案失敗]" + e.getMessage());
		}

	}
}
