package com.qian.gulimall.admin.controller;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.qian.gulimall.admin.api.criteria.SysOssCriteria;
import com.qian.gulimall.admin.entity.SysOssEntity;
import com.qian.gulimall.admin.service.SysOssService;
import com.qian.gulimall.common.utils.PageUtils;
import com.qian.gulimall.common.utils.Pageable;
import com.qian.gulimall.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;



/**
 * 文件上传
 *
 * @author QIAN
 * @email 794308528@qq.com
 * @date 2020-04-19 08:57:20
 */
@RestController
@RequestMapping("sys/oss")
public class SysOssController {
    @Autowired
    private SysOssService sysOssService;

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("admin:sysoss:list")
    public R list(Pageable pageable, @ModelAttribute SysOssCriteria sysOssCriteria){
        PageUtils page = sysOssService.queryPage(pageable, sysOssCriteria);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("admin:sysoss:info")
    public R info(@PathVariable("id") Long id){
		SysOssEntity sysOss = sysOssService.getById(id);

        return R.ok().put("sysOss", sysOss);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("admin:sysoss:save")
    public R save(@RequestBody SysOssEntity sysOss){
		sysOssService.save(sysOss);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("admin:sysoss:update")
    public R update(@RequestBody SysOssEntity sysOss){
		sysOssService.updateById(sysOss);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("admin:sysoss:delete")
    public R delete(@RequestBody Long[] ids){
		sysOssService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 文件上传
     */
    @RequestMapping("/upload")
    public String upload(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();
        long size = file.getSize();
        StorePath storePath = fastFileStorageClient.uploadFile(inputStream, size, fileName.substring(fileName.lastIndexOf(".") + 1), null);
        // TODO 保存到数据库

        return getResAccessUrl(storePath);
    }

    /**
     * 下载文件
     *
     * @param fileUrl
     * @return
     * @throws IOException
     */
    @RequestMapping("/download")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fileUrl = "group1/M00/00/00/qf5zCl8rxVeAAvygAAAADEHIqB4224.txt";  // TODO 从参数中获取ID查询获取
        String group = fileUrl.substring(0, fileUrl.indexOf("/"));
        String path = fileUrl.substring(fileUrl.indexOf("/") + 1);
        DownloadByteArray downloadByteArray = new DownloadByteArray();
        byte[] bytes = fastFileStorageClient.downloadFile(group, path, downloadByteArray);

        String filename = "qian2020080617.txt";
        try (OutputStream outputStream = new FileOutputStream(new File(filename));) {
            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment;filename=" + filename);
            outputStream.write(bytes);
            outputStream.flush();
        }
    }

    /**
     * 测试文件删除
     */
    @RequestMapping("/ossdelete")
    public void deleteFile() {
        fastFileStorageClient.deleteFile("group1", "M00/00/00/wKiAjVlpQVyARpQwAADGA0F72jo566.jpg");
    }


    // 封装文件完整URL地址
    private String getResAccessUrl(StorePath storePath) {
        String fileUrl = storePath.getFullPath();
        return fileUrl;
    }

}
