package com.example.praktam2_2417051011.data.repository

import com.example.praktam2_2417051011.data.model.LocalFile
import com.example.praktam2_2417051011.data.model.Matkul

class MatkulRepository {

    companion object {
        val dummyFiles = mutableListOf<LocalFile>()
    }

    fun getMatkul(): List<Matkul> {
        return listOf(
            Matkul("COM620101", "ALJABAR LINEAR", 1),
            Matkul("COM620102", "BAHASA INGGRIS", 1),
            Matkul("COM620103", "DASAR-DASAR PEMROGRAMAN", 1),
            Matkul("COM620104", "LOGIKA", 1),
            Matkul("COM620105", "MATEMATIKA", 1),
            Matkul("COM620106", "STATISTIK DAN PROBABILITAS", 1),
            Matkul("MIP620101", "SAINS DASAR", 1),
            Matkul("UNI620101", "PENDIDIKAN AGAMA ISLAM", 1),
            Matkul("UNI620102", "PENDIDIKAN AGAMA KATHOLIK", 1),
            Matkul("UNI620103", "PENDIDIKAN AGAMA KRISTEN", 1),
            Matkul("UNI620104", "PENDIDIKAN AGAMA HINDU", 1),
            Matkul("UNI620105", "PENDIDIKAN AGAMA BUDHA", 1),
            Matkul("UNI620109", "PENDIDIKAN ETIKA DAN KEARIFAN LOKAL", 1),
            Matkul("COM620107", "MATEMATIKA DISKRIT", 2),
            Matkul("COM620108", "PEMROGRAMAN TERSTRUKTUR", 2),
            Matkul("COM620109", "PENGANTAR ORGANISASI KOMPUTER", 2),
            Matkul("COM620110", "PENGANTAR SISTEM INFORMASI", 2),
            Matkul("COM620111", "REKAYASA PERANGKAT LUNAK", 2),
            Matkul("COM620112", "SISTEM OPERASI", 2),
            Matkul("COM620113", "STRUKTUR DATA DAN ALGORITMA", 2),
            Matkul("UNI620106", "PENDIDIKAN BAHASA INDONESIA", 2),
            Matkul("UNI620107", "PENDIDIKAN KEWARGANEGARAAN", 2),
            Matkul("COM620201", "ANALISIS NUMERIK", 3),
            Matkul("COM620202", "BASIS DATA", 3),
            Matkul("COM620203", "DESAIN DAN ANALISIS ALGORITMA", 3),
            Matkul("COM620204", "KOMUNIKASI DAN PRESENTASI", 3),
            Matkul("COM620205", "KOMUNIKASI DATA DAN JARINGAN KOMPUTER", 3),
            Matkul("COM620206", "PEMROGRAMAN BERORIENTASI OBJEK", 3),
            Matkul("COM620207", "PENGANTAR SISTEM DIGITAL", 3),
            Matkul("COM620208", "TEORI BAHASA DAN AUTOMATA", 3),
            Matkul("COM620209", "E-SERVICES", 3),
            Matkul("COM620210", "MULTIMEDIA", 3),
            Matkul("COM620211", "PEMROGRAMAN INTERPRETER", 3),
            Matkul("COM620212", "PENGUJIAN PERANGKAT LUNAK", 3),
            Matkul("COM620213", "ANALISIS DAN DESAIN SI", 4),
            Matkul("COM620214", "KECERDASAN BUATAN", 4),
            Matkul("COM620215", "PEMBELAJARAN MESIN", 4),
            Matkul("COM620216", "STUDI LAPANGAN", 4),
            Matkul("COM620217", "TEKNOLOGI DAN APLIKASI MOBILE", 4),
            Matkul("COM620218", "TEORI INFORMASI", 4),
            Matkul("COM620219", "PEMROGRAMAN WEB", 4),
            Matkul("COM620220", "INTERNET OF THINGS", 4),
            Matkul("COM620221", "MANAJEMEN PENGETAHUAN", 4),
            Matkul("COM620222", "PEMROGRAMAN DEKLARATIF", 4),
            Matkul("COM620223", "PEMROSESAN DATA TERDISTRIBUSI", 4),
            Matkul("COM620301", "KEWIRAUSAHAAN", 5),
            Matkul("COM620302", "METODOLOGI PENELITIAN", 5),
            Matkul("COM620303", "PENDIDIKAN PANCASILA", 5),
            Matkul("COM620304", "SISTEM INTERAKSI", 5),
            Matkul("COM620305", "SISTEM PAKAR", 5),
            Matkul("COM620306", "PEMROGRAMAN WEB LANJUT", 5),
            Matkul("COM620307", "CLOUD COMPUTING", 5),
            Matkul("COM620308", "PEMROGRAMAN MOBILE LANJUT", 5),
            Matkul("COM620309", "PEMROSESAN BAHASA ALAMI", 5),
            Matkul("COM620310", "PENGENALAN POLA", 5),
            Matkul("COM620311", "SISTEM INFORMASI GEOGRAFIS", 5),
            Matkul("COM620351", "IDENTIFIKASI KEBUTUHAN TI", 5),
            Matkul("COM620352", "KOMUNIKASI DAN KOLABORASI", 5),
            Matkul("COM620353", "PENGEMBANGAN DAN IMPLEMENTASI TI", 5),
            Matkul("COM620354", "PENULISAN LAPORAN", 5),
            Matkul("COM620355", "PENYUSUNAN PROPOSAL/RENCANA PROYEK", 5),
            Matkul("COM620356", "PENGALAMAN KARIR DAN KEMITRAAN", 5),
            Matkul("COM620312", "ETIKA PROFESI", 6),
            Matkul("COM620313", "KERJA PRAKTIK", 6),
            Matkul("COM620314", "DATA WAREHOUSE DAN BIG DATA", 6),
            Matkul("COM620315", "GRAFIKA KOMPUTER", 6),
            Matkul("COM620316", "KEAMANAN SISTEM INFORMASI", 6),
            Matkul("COM620317", "MANAJEMEN PROYEK TI", 6),
            Matkul("COM620318", "OPERASI RISET", 6),
            Matkul("COM620319", "PENGANTAR ROBOTIK", 6),
            Matkul("COM620320", "PROYEK KHUSUS", 6),
            Matkul("COM620321", "TEMU KEMBALI INFORMASI", 6),
            Matkul("COM620358", "MANAJEMEN RISIKO", 6),
            Matkul("COM620359", "MANAJEMEN SUMBER DAYA MANUSIA", 6),
            Matkul("COM620360", "STUDI KASUS INDUSTRI", 6),
            Matkul("COM620361", "KEPEMIMPINAN DAN BUDAYA ORGANISASI", 6),
            Matkul("COM620362", "MANAJEMEN KEUANGAN USAHA", 6),
            Matkul("COM620401", "KAPITA SELEKTA", 7),
            Matkul("COM620402", "TUGAS KHUSUS", 7),
            Matkul("COM620403", "BIOINFORMATIKA", 7),
            Matkul("COM620404", "BLOCKCHAIN DAN CRYPTO CURRENCY", 7),
            Matkul("COM620405", "KECERDASAN BISNIS", 7),
            Matkul("COM620406", "KOMPUTASI PARALEL", 7),
            Matkul("COM620407", "KOMPUTER DAN MASYARAKAT", 7),
            Matkul("COM620408", "REKAYASA GAME", 7),
            Matkul("COM620409", "AUGMENTED DAN VIRTUAL REALITY", 7),
            Matkul("COM620451", "MANAJEMEN PEMASARAN", 7),
            Matkul("COM620452", "MANAJEMEN PRODUKSI", 7),
            Matkul("COM620453", "MANAJEMEN USAHA KECIL DAN MENENGAH", 7),
            Matkul("COM620454", "PENGALAMAN PEMBANGUNAN DESA", 7),
            Matkul("COM620455", "PENGALAMAN PEMBERDAYAAN MASYARAKAT DESA", 7),
            Matkul("COM620456", "PENGALAMAN PENELITIAN KUALITATIF", 7),
            Matkul("UNI620301", "KULIAH KERJA NYATA (KKN) - KODE 1", 7),
            Matkul("UNI620401", "KULIAH KERJA NYATA (KKN) - KODE 2", 7),
            Matkul("COM620447", "USUL PENELITIAN", 8),
            Matkul("COM620448", "HASIL PENELITIAN", 8),
            Matkul("COM620449", "SKRIPSI", 8),
            Matkul("COM620457", "PENGALAMAN PENELITIAN KUANTITATIF", 8),
            Matkul("COM620458", "PENGEMBANGAN BISNIS", 8),
            Matkul("COM620459", "PENGEMBANGAN MITIGASI BENCANA", 8),
            Matkul("COM620460", "PENGEMBANGAN PROFESIONAL KERJA", 8),
            Matkul("COM620461", "PENULISAN ILMIAH BIDANG ILMU KOMPUTER", 8),
            Matkul("COM620462", "PUBLIKASI ILMIAH", 8)
        )
    }

    fun getFilesByMatkul(kodeMatkul: String, jenis: String): List<LocalFile> {
        return dummyFiles
            .filter { it.kodeMatkul == kodeMatkul && it.jenisDokumen.equals(jenis, ignoreCase = true) }
            .sortedWith(
                compareByDescending<LocalFile> { it.isFavorite }
                    .thenByDescending { it.timestamp }
            )
    }

    fun createFile(kodeMatkul: String, nama: String, jenis: String, filePath: String?) {
        val newFile = LocalFile(
            id = (dummyFiles.maxOfOrNull { it.id } ?: 0) + 1,
            kodeMatkul = kodeMatkul,
            namaFile = nama,
            jenisDokumen = jenis,
            filePath = filePath,
            isFavorite = false,
            timestamp = System.currentTimeMillis()
        )
        dummyFiles.add(newFile)
    }

    fun toggleFavorite(fileId: Int) {
        val index = dummyFiles.indexOfFirst { it.id == fileId }
        if (index != -1) {
            val fileLama = dummyFiles[index]
            dummyFiles[index] = fileLama.copy(isFavorite = !fileLama.isFavorite)
        }
    }

    fun updateFileName(fileId: Int, namaBaru: String) {
        val index = dummyFiles.indexOfFirst { it.id == fileId }
        if (index != -1) {
            val fileLama = dummyFiles[index]
            dummyFiles[index] = fileLama.copy(namaFile = namaBaru, timestamp = System.currentTimeMillis())
        }
    }

    fun deleteFile(fileId: Int) {
        dummyFiles.removeAll { it.id == fileId }
    }

    fun searchFiles(query: String): List<LocalFile> {
        return if (query.isBlank()) {
            dummyFiles
        } else {
            dummyFiles.filter { it.namaFile.contains(query, ignoreCase = true) }
        }
    }
}