import JSEncrypt from 'jsencrypt/bin/jsencrypt.min'

// 密钥对生成 http://web.chacuo.net/netrsakeypair

const publicKey = 'MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA483mAcocXVT7PaTks+Fv\n'+
                  'ZTomll6tzBC4U0FZaPBxEaRHyStOzUfBVwOrqnySI0wgpH8f/MFzyUZrPzk7vfW8\n'+
                  'csoT0cFWMNuNj+Gk7/N5XVVZfD8M7I4MeIX1bPLBvN98pXe9XR3aCsjR37mnrCvg\n'+
                  'yTn7Wkzjq/uOmXt4O38PWM8GaJ7Me7pIL9g84McyXlZ9mb1JEHSEo496hRMt0VVB\n'+
                  'iUglrK6jgS13i4mpB9aisApgSogjWDtOD0GOg8WE/jV9DIQ0shxN+TvQLD4v2R66\n'+
                  'KfVNHZYZ+OSyVlZ5cdhs4fZjrcHRxn8q837f12Ru1/9Lbif/FXiF2SzJIwd+rPk8\n'+
                  'eQIDAQAB'

const privateKey = 'MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDjzeYByhxdVPs9\n'+
                   'pOSz4W9lOiaWXq3MELhTQVlo8HERpEfJK07NR8FXA6uqfJIjTCCkfx/8wXPJRms/\n'+
                   'OTu99bxyyhPRwVYw242P4aTv83ldVVl8Pwzsjgx4hfVs8sG833yld71dHdoKyNHf\n'+
                   'uaesK+DJOftaTOOr+46Ze3g7fw9YzwZonsx7ukgv2DzgxzJeVn2ZvUkQdISjj3qF\n'+
                   'Ey3RVUGJSCWsrqOBLXeLiakH1qKwCmBKiCNYO04PQY6DxYT+NX0MhDSyHE35O9As\n'+
                   'Pi/ZHrop9U0dlhn45LJWVnlx2Gzh9mOtwdHGfyrzft/XZG7X/0tuJ/8VeIXZLMkj\n'+
                   'B36s+Tx5AgMBAAECggEAI8cxybQpkksHjp34QXw1l6tzZgiGuVci7AiGd2HUEFZB\n'+
                   '5AmGPd14MydjbcpU1XBebBd/OZ5UhhcmPu7JQEN5DnpIdJgt5kyFRkTFN96AgDUo\n'+
                   'ccghSoHg2YkIi8zwuq5LEF3nAtnuZeU2eTHacMMPcetGW99ZnqhKtDZdItMI4QSO\n'+
                   '4wtQquWO2k12KysRNnV/zE3ANcgbexqzVd6O/8tv1r0dVz5KMZz3eYfZmkyS+i0u\n'+
                   'Md5JfNrm5pPF5uxP7zT/ueZOmMl+vsHT2pKXDuXFMgbuKCpe3ccQl5n9ExdURU9Y\n'+
                   'qsqMLWiy6JpJZHtcWXXbQblYU5ZwyxC/xNrLRyPbBQKBgQD+Q6GRF8PKh9HgKSQ3\n'+
                   '2k2J69iw0AHHxIfEuXxYy5TcW9bMJYEOYrgLYeDScl4K8bEkClqFw9uFbbyIIhC8\n'+
                   '5QfA5sKDVGk1kfxlqOLqT+20Loy9JcS3mxUtE6XkXutVk56BrKLWh98lzO6B9TBc\n'+
                   '3/vMtvMpZAqroAKwGCvtjFTPtwKBgQDlXAY3uqiTTD32blXLJdRq16m8jPG/s4aX\n'+
                   'qXfrnyWOSQ0RGA/5jTTawL6SiEjuuX6oSlBNV2FOk2BA1jKon49Ofhk6NJXIoobO\n'+
                   'aTsMMgv82/4u8Vz2BgRJxtddXDySFGC80YMWzZaWBK81DPz1q3Rq51eagEoNnqAw\n'+
                   '1EavJhX1TwKBgQCbkYGvJp0ys7sjrchtK/I5KsYgGISeV47CPMLqCVWBJefcbC30\n'+
                   'QU5eGHrYCAWmKI67gGI2aclMcAHkQQOAr5j427ezZtggYLvO73A28MR9c+XEbPFZ\n'+
                   'bVedhuH/Qlw2teVLbfcLz4ImvKZJeV0n1htX+6/3aTBmJba/S2rFIxFZLQKBgQC2\n'+
                   '1olOW8qOwbSTgpl5/Io9MfbpjCIbg+3DcSFb/95ccverrNbvRRXhXM2O2n3pcI37\n'+
                   'cmJZhVLY5LtSdG6l5azEEdnigJD3BDkayuB7dFoCFQ2oNli490rr1UtR7XmLqhsD\n'+
                   '6rDpuLJWnR1e2R++aBPCNPGtBKAEA3QH+PDwJxhSGQKBgQCaG8NpRznT6BlUnDNY\n'+
                   'axMC9YYcRWS+IASIQEiMWxATGOJn+igogDPfGURMCN0W/zwEyHG7LZSrcLNN1e+M\n'+
                   'nM31iBEEaqXcBxpKpLZeQizZJ3I+Fb4HzRwrvBUIr1H5vpQ7fDO/DUSOj2XYvMs0\n'+
                   '1C6A0akwrOnK6YOUIjORQ2u//A=='

// 加密
export function encrypt(txt) {
  const encryptor = new JSEncrypt()
  encryptor.setPublicKey(publicKey) // 设置公钥
  return encryptor.encrypt(txt) // 对数据进行加密
}

// 解密
export function decrypt(txt) {
  const encryptor = new JSEncrypt()
  encryptor.setPrivateKey(privateKey) // 设置私钥
  return encryptor.decrypt(txt) // 对数据进行解密
}

