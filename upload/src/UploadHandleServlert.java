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
			//�õ��ϴ��ļ��ı���Ŀ¼�����ϴ����ļ������WEB-INFĿ¼�£����������ֱ�ӷ��ʣ���֤�ϴ��ļ��İ�ȫ
			String savePath = this.getServletContext().getRealPath("/WEB-INF/upload");
			 //�ϴ�ʱ���ɵ���ʱ�ļ�����Ŀ¼
			String tempPath = this.getServletContext().getRealPath("/WEB-INF/temp");
			File tmpFile = new File(tempPath);
			if(!tmpFile.exists())
			{
				//����Ŀ¼
				tmpFile.mkdir();
			}
			
			String message = "";
			
			try
			{
				//ʹ��Apache�ļ��ϴ���������ļ��ϴ����裺
				//1������һ��DiskFileItemFactory����
				DiskFileItemFactory factory = new DiskFileItemFactory();
				//���û������Ĵ�СΪ100KB�������ָ������ô�������Ĵ�СĬ����10KB
				factory.setSizeThreshold(1024*100);
				//�����ϴ�ʱ���ɵ���ʱ�ļ��ı���Ŀ¼
				factory.setRepository(tmpFile);
				//2������һ���ļ��ϴ�������
				ServletFileUpload upload = new ServletFileUpload(factory);
				upload.setProgressListener(new ProgressListener()
				{
					public void update(long pBytesRead,long pContentLength,int arg2)
					{
						System.out.println("�ļ���СΪ��" + pContentLength + ",�Ѿ��ϴ�:" +  pBytesRead);
					}
				});
				 //����ϴ��ļ�������������
				upload.setHeaderEncoding("utf-8");
				//3���ж��ύ�����������Ƿ����ϴ���������
				if(!ServletFileUpload.isMultipartContent(request))
				{
					return;
				}
				 //�����ϴ������ļ��Ĵ�С�����ֵ��Ŀǰ������Ϊ1024*1024�ֽڣ�Ҳ����1MB
				upload.setFileSizeMax(1024*1024);
				 //�����ϴ��ļ����������ֵ�����ֵ=ͬʱ�ϴ��Ķ���ļ��Ĵ�С�����ֵ�ĺͣ�Ŀǰ����Ϊ10MB
				upload.setSizeMax(1024*1024*10);
				//�������е�ÿһ�������װ��FileItem������List��ʽ���ء�
				//4��ʹ��ServletFileUpload�����������ϴ����ݣ�����������ص���һ��List<FileItem>���ϣ�
				//ÿһ��FileItem��Ӧһ��Form����������
				List<FileItem> list = upload.parseRequest(request);
				WJBean wb = new WJBean();
				HttpSession session =request.getSession();
				System.out.println("sdfsadfdsafasdfasdfasdfasdfasdfadsf");
				System.out.println(session.getAttribute("username"));
				wb.setUsername("user");
				System.out.println(wb.getUsername());
				for(FileItem item : list)
				{
					//���fileitem�з�װ������ͨ�����������
					if(item.isFormField())
					{
						String name = item.getFieldName();
						//�����ͨ����������ݵ�������������
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
						//���fileitem�з�װ�����ϴ��ļ�
						//�õ��ϴ����ļ����ƣ�
						String filename = item.getName();
						System.out.println(filename);
						if(filename == null || filename.trim().equals(""))
						{
							continue;
						}
						//�����ȡ�����ϴ��ļ����ļ�����·�����֣�ֻ�����ļ�������
						filename = filename.substring(filename.lastIndexOf("\\") + 1);
						 //�õ��ϴ��ļ�����չ��
						String fileExtName = filename.substring(filename.lastIndexOf(".") + 1);
						System.out.println("�ϴ��ļ�����չ���ǣ�" + fileExtName);
						
						//��ȡitem�е��ϴ��ļ���������
						InputStream in = item.getInputStream();
						//�õ��ļ����������
						String saveFilename = makeFileName(filename);
						//�õ��ļ��ı���Ŀ¼
						String realsaveFilePath = makePath(saveFilename,savePath);
						System.out.println(realsaveFilePath+ "\\" + saveFilename);
						 //����һ���ļ������
						FileOutputStream out = new FileOutputStream(realsaveFilePath + "\\" + saveFilename);
						//����һ��������
						byte buffer[] = new byte[1024];
						
						int len = 0;
						//ѭ�������������뵽���������У�(len=in.read(buffer))>0�ͱ�ʾin���滹������
						while((len = in.read(buffer)) > 0)
						{
							//ʹ��FileOutputStream�������������������д�뵽ָ����Ŀ¼
							//(savePath + "\\" + filename)����
							out.write(buffer , 0 , len);
						}
						//�ر�������
						in.close();
						//�ر������
						out.close();
						 //ɾ�������ļ��ϴ�ʱ���ɵ���ʱ�ļ�
						item.delete();
						message = "success!";
					}
				}
				
			}catch (FileUploadBase.FileSizeLimitExceededException e) {
				// TODO: handle exception
				e.printStackTrace();
				request.setAttribute("message", "�����ļ��������ֵ");
				request.getRequestDispatcher("message.jsp").forward(request, response);
				return;
			}catch(FileUploadBase.SizeLimitExceededException e)
			{
				e.printStackTrace();
				request.setAttribute("message", "�ļ��ܴ�С�������ֵ");
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
		//Ϊ��ֹ�ļ����ǵ���������ҪΪ�ϴ��ļ�����һ��Ψһ���ļ���
		return UUID.randomUUID().toString() + "_" + filename;
	}
	//Ϊ��ֹһ��Ŀ¼�������̫���ļ���Ҫʹ��hash�㷨��ɢ�洢
	private String makePath(String filename,String savePath)
	{
		//�õ��ļ�����hashCode��ֵ���õ��ľ���filename����ַ����������ڴ��еĵ�ַ
		int hashcode = filename.hashCode();
		int dir1 = hashcode&0xf;
		int dir2 = (hashcode&0xf0)>>4;
		 //�����µı���Ŀ¼
		String dir = savePath + "\\" + dir1 + "\\" + dir2;
		//upload\2\3  upload\3\5
		//File�ȿ��Դ����ļ�Ҳ���Դ���Ŀ¼
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
