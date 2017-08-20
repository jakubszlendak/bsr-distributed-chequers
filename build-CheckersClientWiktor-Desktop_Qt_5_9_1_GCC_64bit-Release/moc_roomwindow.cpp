/****************************************************************************
** Meta object code from reading C++ file 'roomwindow.h'
**
** Created by: The Qt Meta Object Compiler version 67 (Qt 5.9.1)
**
** WARNING! All changes made in this file will be lost!
*****************************************************************************/

#include "../CheckersClientWiktor/roomwindow.h"
#include <QtCore/qbytearray.h>
#include <QtCore/qmetatype.h>
#if !defined(Q_MOC_OUTPUT_REVISION)
#error "The header file 'roomwindow.h' doesn't include <QObject>."
#elif Q_MOC_OUTPUT_REVISION != 67
#error "This file was generated using the moc from 5.9.1. It"
#error "cannot be used with the include files from this version of Qt."
#error "(The moc has changed too much.)"
#endif

QT_BEGIN_MOC_NAMESPACE
QT_WARNING_PUSH
QT_WARNING_DISABLE_DEPRECATED
struct qt_meta_stringdata_RoomWindow_t {
    QByteArrayData data[9];
    char stringdata0[136];
};
#define QT_MOC_LITERAL(idx, ofs, len) \
    Q_STATIC_BYTE_ARRAY_DATA_HEADER_INITIALIZER_WITH_OFFSET(len, \
    qptrdiff(offsetof(qt_meta_stringdata_RoomWindow_t, stringdata0) + ofs \
        - idx * sizeof(QByteArrayData)) \
    )
static const qt_meta_stringdata_RoomWindow_t qt_meta_stringdata_RoomWindow = {
    {
QT_MOC_LITERAL(0, 0, 10), // "RoomWindow"
QT_MOC_LITERAL(1, 11, 19), // "refreshButtonSignal"
QT_MOC_LITERAL(2, 31, 0), // ""
QT_MOC_LITERAL(3, 32, 6), // "invite"
QT_MOC_LITERAL(4, 39, 4), // "user"
QT_MOC_LITERAL(5, 44, 6), // "logout"
QT_MOC_LITERAL(6, 51, 28), // "on_refreshPushButton_clicked"
QT_MOC_LITERAL(7, 80, 27), // "on_logoutPushButton_clicked"
QT_MOC_LITERAL(8, 108, 27) // "on_invitePushButton_clicked"

    },
    "RoomWindow\0refreshButtonSignal\0\0invite\0"
    "user\0logout\0on_refreshPushButton_clicked\0"
    "on_logoutPushButton_clicked\0"
    "on_invitePushButton_clicked"
};
#undef QT_MOC_LITERAL

static const uint qt_meta_data_RoomWindow[] = {

 // content:
       7,       // revision
       0,       // classname
       0,    0, // classinfo
       6,   14, // methods
       0,    0, // properties
       0,    0, // enums/sets
       0,    0, // constructors
       0,       // flags
       3,       // signalCount

 // signals: name, argc, parameters, tag, flags
       1,    0,   44,    2, 0x06 /* Public */,
       3,    1,   45,    2, 0x06 /* Public */,
       5,    0,   48,    2, 0x06 /* Public */,

 // slots: name, argc, parameters, tag, flags
       6,    0,   49,    2, 0x08 /* Private */,
       7,    0,   50,    2, 0x08 /* Private */,
       8,    0,   51,    2, 0x08 /* Private */,

 // signals: parameters
    QMetaType::Void,
    QMetaType::Void, QMetaType::QString,    4,
    QMetaType::Void,

 // slots: parameters
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void,

       0        // eod
};

void RoomWindow::qt_static_metacall(QObject *_o, QMetaObject::Call _c, int _id, void **_a)
{
    if (_c == QMetaObject::InvokeMetaMethod) {
        RoomWindow *_t = static_cast<RoomWindow *>(_o);
        Q_UNUSED(_t)
        switch (_id) {
        case 0: _t->refreshButtonSignal(); break;
        case 1: _t->invite((*reinterpret_cast< QString(*)>(_a[1]))); break;
        case 2: _t->logout(); break;
        case 3: _t->on_refreshPushButton_clicked(); break;
        case 4: _t->on_logoutPushButton_clicked(); break;
        case 5: _t->on_invitePushButton_clicked(); break;
        default: ;
        }
    } else if (_c == QMetaObject::IndexOfMethod) {
        int *result = reinterpret_cast<int *>(_a[0]);
        void **func = reinterpret_cast<void **>(_a[1]);
        {
            typedef void (RoomWindow::*_t)();
            if (*reinterpret_cast<_t *>(func) == static_cast<_t>(&RoomWindow::refreshButtonSignal)) {
                *result = 0;
                return;
            }
        }
        {
            typedef void (RoomWindow::*_t)(QString );
            if (*reinterpret_cast<_t *>(func) == static_cast<_t>(&RoomWindow::invite)) {
                *result = 1;
                return;
            }
        }
        {
            typedef void (RoomWindow::*_t)();
            if (*reinterpret_cast<_t *>(func) == static_cast<_t>(&RoomWindow::logout)) {
                *result = 2;
                return;
            }
        }
    }
}

const QMetaObject RoomWindow::staticMetaObject = {
    { &QDialog::staticMetaObject, qt_meta_stringdata_RoomWindow.data,
      qt_meta_data_RoomWindow,  qt_static_metacall, nullptr, nullptr}
};


const QMetaObject *RoomWindow::metaObject() const
{
    return QObject::d_ptr->metaObject ? QObject::d_ptr->dynamicMetaObject() : &staticMetaObject;
}

void *RoomWindow::qt_metacast(const char *_clname)
{
    if (!_clname) return nullptr;
    if (!strcmp(_clname, qt_meta_stringdata_RoomWindow.stringdata0))
        return static_cast<void*>(const_cast< RoomWindow*>(this));
    return QDialog::qt_metacast(_clname);
}

int RoomWindow::qt_metacall(QMetaObject::Call _c, int _id, void **_a)
{
    _id = QDialog::qt_metacall(_c, _id, _a);
    if (_id < 0)
        return _id;
    if (_c == QMetaObject::InvokeMetaMethod) {
        if (_id < 6)
            qt_static_metacall(this, _c, _id, _a);
        _id -= 6;
    } else if (_c == QMetaObject::RegisterMethodArgumentMetaType) {
        if (_id < 6)
            *reinterpret_cast<int*>(_a[0]) = -1;
        _id -= 6;
    }
    return _id;
}

// SIGNAL 0
void RoomWindow::refreshButtonSignal()
{
    QMetaObject::activate(this, &staticMetaObject, 0, nullptr);
}

// SIGNAL 1
void RoomWindow::invite(QString _t1)
{
    void *_a[] = { nullptr, const_cast<void*>(reinterpret_cast<const void*>(&_t1)) };
    QMetaObject::activate(this, &staticMetaObject, 1, _a);
}

// SIGNAL 2
void RoomWindow::logout()
{
    QMetaObject::activate(this, &staticMetaObject, 2, nullptr);
}
QT_WARNING_POP
QT_END_MOC_NAMESPACE
