/****************************************************************************
** Meta object code from reading C++ file 'controler.h'
**
** Created by: The Qt Meta Object Compiler version 67 (Qt 5.9.1)
**
** WARNING! All changes made in this file will be lost!
*****************************************************************************/

#include "../CheckersClientWiktor/controler.h"
#include <QtCore/qbytearray.h>
#include <QtCore/qmetatype.h>
#if !defined(Q_MOC_OUTPUT_REVISION)
#error "The header file 'controler.h' doesn't include <QObject>."
#elif Q_MOC_OUTPUT_REVISION != 67
#error "This file was generated using the moc from 5.9.1. It"
#error "cannot be used with the include files from this version of Qt."
#error "(The moc has changed too much.)"
#endif

QT_BEGIN_MOC_NAMESPACE
QT_WARNING_PUSH
QT_WARNING_DISABLE_DEPRECATED
struct qt_meta_stringdata_Controler_t {
    QByteArrayData data[16];
    char stringdata0[152];
};
#define QT_MOC_LITERAL(idx, ofs, len) \
    Q_STATIC_BYTE_ARRAY_DATA_HEADER_INITIALIZER_WITH_OFFSET(len, \
    qptrdiff(offsetof(qt_meta_stringdata_Controler_t, stringdata0) + ofs \
        - idx * sizeof(QByteArrayData)) \
    )
static const qt_meta_stringdata_Controler_t qt_meta_stringdata_Controler = {
    {
QT_MOC_LITERAL(0, 0, 9), // "Controler"
QT_MOC_LITERAL(1, 10, 5), // "login"
QT_MOC_LITERAL(2, 16, 0), // ""
QT_MOC_LITERAL(3, 17, 8), // "password"
QT_MOC_LITERAL(4, 26, 6), // "regist"
QT_MOC_LITERAL(5, 33, 15), // "commandReceived"
QT_MOC_LITERAL(6, 49, 11), // "fullCommand"
QT_MOC_LITERAL(7, 61, 8), // "fllCmmnd"
QT_MOC_LITERAL(8, 70, 17), // "refreshButtonSlot"
QT_MOC_LITERAL(9, 88, 6), // "logout"
QT_MOC_LITERAL(10, 95, 6), // "invite"
QT_MOC_LITERAL(11, 102, 4), // "user"
QT_MOC_LITERAL(12, 107, 22), // "squareClickedWithMouse"
QT_MOC_LITERAL(13, 130, 7), // "Square*"
QT_MOC_LITERAL(14, 138, 6), // "square"
QT_MOC_LITERAL(15, 145, 6) // "giveUp"

    },
    "Controler\0login\0\0password\0regist\0"
    "commandReceived\0fullCommand\0fllCmmnd\0"
    "refreshButtonSlot\0logout\0invite\0user\0"
    "squareClickedWithMouse\0Square*\0square\0"
    "giveUp"
};
#undef QT_MOC_LITERAL

static const uint qt_meta_data_Controler[] = {

 // content:
       7,       // revision
       0,       // classname
       0,    0, // classinfo
       8,   14, // methods
       0,    0, // properties
       0,    0, // enums/sets
       0,    0, // constructors
       0,       // flags
       0,       // signalCount

 // slots: name, argc, parameters, tag, flags
       1,    2,   54,    2, 0x0a /* Public */,
       4,    2,   59,    2, 0x0a /* Public */,
       5,    1,   64,    2, 0x0a /* Public */,
       8,    0,   67,    2, 0x0a /* Public */,
       9,    0,   68,    2, 0x0a /* Public */,
      10,    1,   69,    2, 0x0a /* Public */,
      12,    1,   72,    2, 0x0a /* Public */,
      15,    0,   75,    2, 0x0a /* Public */,

 // slots: parameters
    QMetaType::Void, QMetaType::QString, QMetaType::QString,    1,    3,
    QMetaType::Void, QMetaType::QString, QMetaType::QString,    1,    3,
    QMetaType::Void, 0x80000000 | 6,    7,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void, QMetaType::QString,   11,
    QMetaType::Void, 0x80000000 | 13,   14,
    QMetaType::Void,

       0        // eod
};

void Controler::qt_static_metacall(QObject *_o, QMetaObject::Call _c, int _id, void **_a)
{
    if (_c == QMetaObject::InvokeMetaMethod) {
        Controler *_t = static_cast<Controler *>(_o);
        Q_UNUSED(_t)
        switch (_id) {
        case 0: _t->login((*reinterpret_cast< QString(*)>(_a[1])),(*reinterpret_cast< QString(*)>(_a[2]))); break;
        case 1: _t->regist((*reinterpret_cast< QString(*)>(_a[1])),(*reinterpret_cast< QString(*)>(_a[2]))); break;
        case 2: _t->commandReceived((*reinterpret_cast< fullCommand(*)>(_a[1]))); break;
        case 3: _t->refreshButtonSlot(); break;
        case 4: _t->logout(); break;
        case 5: _t->invite((*reinterpret_cast< QString(*)>(_a[1]))); break;
        case 6: _t->squareClickedWithMouse((*reinterpret_cast< Square*(*)>(_a[1]))); break;
        case 7: _t->giveUp(); break;
        default: ;
        }
    } else if (_c == QMetaObject::RegisterMethodArgumentMetaType) {
        switch (_id) {
        default: *reinterpret_cast<int*>(_a[0]) = -1; break;
        case 6:
            switch (*reinterpret_cast<int*>(_a[1])) {
            default: *reinterpret_cast<int*>(_a[0]) = -1; break;
            case 0:
                *reinterpret_cast<int*>(_a[0]) = qRegisterMetaType< Square* >(); break;
            }
            break;
        }
    }
}

const QMetaObject Controler::staticMetaObject = {
    { &QObject::staticMetaObject, qt_meta_stringdata_Controler.data,
      qt_meta_data_Controler,  qt_static_metacall, nullptr, nullptr}
};


const QMetaObject *Controler::metaObject() const
{
    return QObject::d_ptr->metaObject ? QObject::d_ptr->dynamicMetaObject() : &staticMetaObject;
}

void *Controler::qt_metacast(const char *_clname)
{
    if (!_clname) return nullptr;
    if (!strcmp(_clname, qt_meta_stringdata_Controler.stringdata0))
        return static_cast<void*>(const_cast< Controler*>(this));
    return QObject::qt_metacast(_clname);
}

int Controler::qt_metacall(QMetaObject::Call _c, int _id, void **_a)
{
    _id = QObject::qt_metacall(_c, _id, _a);
    if (_id < 0)
        return _id;
    if (_c == QMetaObject::InvokeMetaMethod) {
        if (_id < 8)
            qt_static_metacall(this, _c, _id, _a);
        _id -= 8;
    } else if (_c == QMetaObject::RegisterMethodArgumentMetaType) {
        if (_id < 8)
            qt_static_metacall(this, _c, _id, _a);
        _id -= 8;
    }
    return _id;
}
QT_WARNING_POP
QT_END_MOC_NAMESPACE
