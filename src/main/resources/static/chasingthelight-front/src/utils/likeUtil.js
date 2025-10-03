import request from './request.js';

/**
 * 处理相机点赞/取消点赞
 * @param {Object} camera 相机对象
 * @returns {Promise<boolean>} 是否操作成功
 */
export const addCameraLike = async (camera) => {
    try {
        const token = localStorage.getItem('token');
        console.log('当前token:', token); // 检查是否有token
        // 发送请求到后端
        const response = await request.post('/camera/addCameralike', {
            cameraId: Number(camera.cameraId)
        }, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        console.log( response.data.message);
        return false;
    } catch (error) {
        console.error('点赞请求异常:', error);
        return false;
    }
};

export const removeCameraLike = async (camera) => {
    try{
        const token = localStorage.getItem('token');
        console.log('当前token:', token); // 检查是否有token
        // 发送请求到后端
        const response = await request.post('/camera/reduceCameralike', {
            cameraId: Number(camera.cameraId)
        }, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });;

        console.log( response.data.message);
        return false;
    } catch (error) {
        console.error('点赞请求异常:', error);
        return false;
    }

}
