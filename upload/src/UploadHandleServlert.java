import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.lindi.login.*;
import java.util.List;
import java.util.UUID;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;



public class UploadHandleServlert extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
		{
			//得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
			String savePath = this.getServletContext().getRealPath("/WEB-INF/upload");
			 //上传时生成的临时文件保存目录
			String tempPath = this.getServletContext().getRealPath("/WEB-INF/temp");
			File tmpFile = new File(tempPath);
			if(!tmpFile.exists())
			{
				//创建目录
				tmpFile.mkdir();
			}
			
			String message = "";
			
			try
			{
				//使用Apache文件上传组件处理文件上传步骤：
				//1、创建一个DiskFileItemFactory工厂
				DiskFileItemFactory factory = new DiskFileItemFactory();
				//设置缓冲区的大小为100KB，如果不指定，那么缓冲区的大小默认是10KB
				factory.setSizeThreshold(1024*100);
				//设置上传时生成的临时文件的保存目录
				factory.setRepository(tmpFile);
				//2、创建一个文件上传解析器
				ServletFileUpload upload = new ServletFileUpload(factory);
				upload.setProgressListener(new ProgressListener()
				{
					public void update(long pBytesRead,long pContentLength,int arg2)
					{
						System.out.println("文件大小为：" + pContentLength + ",已经上传:" +  pBytesRead);
					}
				});
				 //解决上传文件名的中文乱码
				upload.setHeaderEncoding("utf-8");
				//3、判断提交上来的数据是否是上传表单的数据
				if(!ServletFileUpload.isMultipartContent(request))
				{
					return;
				}
				 //设置上传单个文件的大小的最大值，目前是设置为1024*1024字节，也就是1MB
				upload.setFileSizeMax(1024*1024);
				 //设置上传文件总量的最大值，最大值=同时上传的多个文件的大小的最大值的和，目前设置为10MB
				upload.setSizeMax(1024*1024*10);
				//解析表单中的每一个表单项，封装成FileItem对象，以List方式返回。
				//4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，
				//每一个FileItem对应一个Form表单的输入项
				List<FileItem> list = upload.parseRequest(request);
				WJBean wb = new WJBean();
				HttpSession session =request.getSession();
				System.out.println("sdfsadfdsafasdfasdfasdfasdfasdfadsf");
				System.out.println(session.getAttribute("username"));
				wb.setUsername("user");
				System.out.println(wb.getUsername());
				for(FileItem item : list)
				{
					//如果fileitem中封装的是普通输入项的数据
					if(item.isFormField())
					{
						String name = item.getFieldName();
						//解决普通输入项的数据的中文乱码问题
						String value = item.getString("utf-8");
						if(name.equals("username"))
						{
							wb.setUsername(value);
							System.out.println(name + "=" + wb.getUsername());
						}
						if(name.equals("BT"))
						{
							wb.setBT(value);
							System.out.println(name + "=" + wb.getBT());
						}
						
					}else
					{
						//如果fileitem中封装的是上传文件
						//得到上传的文件名称，
						String filename = item.getName();
						System.out.println(filename);
						if(filename == null || filename.trim().equals(""))
						{
							continue;
						}
						//处理获取到的上传文件的文件名的路径部分，只保留文件名部分
						filename = filename.substring(filename.lastIndexOf("\\") + 1);
						 //得到上传文件的扩展名
						String fileExtName = filename.substring(filename.lastIndexOf(".") + 1);
						System.out.println("上传文件的扩展名是：" + fileExtName);
						
						//获取item中的上传文件的输入流
						InputStream in = item.getInputStream();
						//得到文件保存的名称
						String saveFilename = makeFileName(filename);
						//得到文件的保存目录
						String realsaveFilePath = makePath(saveFilename,savePath);
						System.out.println(realsaveFilePath+ "\\" + saveFilename);
						 //创建一个文件输出流
						FileOutputStream out = new FileOutputStream(realsaveFilePath + "\\" + saveFilename);
						//创建一个缓冲区
						byte buffer[] = new byte[1024];
						
						int len = 0;
						//循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
						while((len = in.read(buffer)) > 0)
						{
							//使用FileOutputStream输出流将缓冲区的数据写入到指定的目录
							//(savePath + "\\" + filename)当中
							out.write(buffer , 0 , len);
						}
						//关闭输入流
						in.close();
						//关闭输出流
						out.close();
						 //删除处理文件上传时生成的临时文件
						item.delete();
						message = "success!";
					}
				}
				
			}catch (FileUploadBase.FileSizeLimitExceededException e) {
				// TODO: handle exception
				e.printStackTrace();
				request.setAttribute("message", "单个文件超出最大值");
				request.getRequestDispatcher("message.jsp").forward(request, response);
				return;
			}catch(FileUploadBase.SizeLimitExceededException e)
			{
				e.printStackTrace();
				request.setAttribute("message", "文件总大小超出最大值");
				request.getRequestDispatcher("message.jsp").forward(request, response);
				return;
			}catch(Exception e)
			{
				message = "fail";
				e.printStackTrace();
			}
			request.setAttribute("message", message);
			request.getRequestDispatcher("message.jsp").forward(request, response);		
			
	}
	private String makeFileName(String filename)
	{
		//为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名
		return UUID.randomUUID().toString() + "_" + filename;
	}
	//为防止一个目录下面出现太多文件，要使用hash算法打散存储
	private String makePath(String filename,String savePath)
	{
		//得到文件名的hashCode的值，得到的就是filename这个字符串对象在内存中的地址
		int hashcode = filename.hashCode();
		int dir1 = hashcode&0xf;
		int dir2 = (hashcode&0xf0)>>4;
		 //构造新的保存目录
		String dir = savePath + "\\" + dir1 + "\\" + dir2;
		//upload\2\3  upload\3\5
		//File既可以代表文件也可以代表目录
		File file = new File(dir);
		if(!file.exists())
		{
			file.mkdirs();
		}
		return dir;
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request,response);
	}


}
